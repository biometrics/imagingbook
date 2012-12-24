/**
 * This sample code is made available as part of the book "Digital Image
 * Processing - An Algorithmic Introduction using Java" by Wilhelm Burger
 * and Mark J. Burge, Copyright (C) 2005-2008 Springer-Verlag Berlin, 
 * Heidelberg, New York.
 * Note that this code comes with absolutely no warranty of any kind.
 * See http://www.imagingbook.com for details and licensing conditions.
 * 
 * Date: 2010/08/01
 */

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.List;
import regions.BinaryRegion;
import regions.RegionLabeling;
import contours.Contour;
import contours.ContourOverlay;
import contours.ContourTracer;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.ImageWindow;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/*
	This ImageJ plugin implements the combined contour tracing and 
	component labeling algorithm as described in  Chang, Chun-Jen: 
	``A Component-Labeling Algorithm Using Contour Tracing 
	Technique'', Proc. ICDAR03, p. 741-75, IEEE Comp. Soc., 2003.
	It uses the ContourTracer class to create lists of points 
	representing the internal and external contours of each region in
	the binary image.  Instead of drawing directly into the image, 
	we make use of ImageJ's Class ImageCanvas to draw the contours 
	in a separate layer on top of the image.  Illustrates how to use 
	the Java2D API to draw the polygons and scale and transform them 
	to match ImageJ's zooming.  

	2010-08-01 Cleanup, added mu_11() example.
*/

public class Trace_Contours implements PlugInFilter {
	
	ImagePlus origImage = null;
	String origTitle = null;
	static boolean BEVERBOSE = true;
	
	public int setup(String arg, ImagePlus im) { 
    	origImage = im;
		origTitle = im.getTitle();
		RegionLabeling.setVerbose(BEVERBOSE);
		return DOES_8G + NO_CHANGES; 
	}
	
	public void run(ImageProcessor ip) {
		ImageProcessor ip2 = ip.duplicate();
		//  label regions and trace contours
		ContourTracer tracer = new ContourTracer(ip2);
		
		// extract contours and regions
		List<Contour> outerContours = tracer.getOuterContours();
		List<Contour> innerContours = tracer.getInnerContours();

		// change lookup-table to show gray regions
		ip2.setMinAndMax(0,512);
		// create an image with overlay to show the contours
		ImagePlus im2 = new ImagePlus("Contours of " + origTitle, ip2);
		ContourOverlay cc = new ContourOverlay(im2, outerContours, innerContours);
		new ImageWindow(im2, cc);
		
		if (BEVERBOSE) {
			List<BinaryRegion> regions = tracer.getRegions();
			for (BinaryRegion r: regions) {
				Point2D ctr = r.getCenter();
				double mu11 = mu_11(tracer, r);
				IJ.log("Region " + r.getLabel() + ": ctr=" + ctr.getX() + "/" + ctr.getY() + ", mu11=" + mu11);
			}
		}
	}
	
    /*
     * This method demonstrates how a particular region's central moment
     * mu_11 could be calculated from the finished region labeling.
     */
    double mu_11 (ContourTracer tracer, BinaryRegion r) {
    	int label = r.getLabel();
    	Rectangle bb = r.getBoundingBox();
    	double xc = r.getCenter().x;	// centroid of this region
    	double yc = r.getCenter().y;
    	double s11 = 0;	// x/y sums
    	// collect all coordinates with exactly this label
    	for (int v=bb.y; v<bb.y+bb.height; v++) {
    		for (int u=bb.x; u<bb.x+bb.width; u++) {
    			if (tracer.getLabel(u,v) == label) {
    				s11 = s11 + (u - xc) * (v - yc);
    			}
    		}
    	}
    	return s11;
    }
}