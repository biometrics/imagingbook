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

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

import java.awt.Rectangle;
import java.util.List;

import regions.BinaryRegion;
import regions.DepthFirstLabeling;
import regions.RegionLabeling;

/*
 * This ImageJ plugin is an example for how to use the region
 * labeling classes in the "regions" package.
 * One of four labeling types can be selected (see run() method).
 * They should all return the same result.
 * 
 * 2009-11-15: Cleanup, added mu_11() example.
*/

public class Apply_Region_Labeling implements PlugInFilter {
	
	ImagePlus origImage = null;
	String origTitle = null;
	static boolean BEVERBOSE = false;
	static boolean RECOLOR = false;
	
    public int setup(String arg, ImagePlus im) {
    	origImage = im;
		origTitle = im.getTitle();
	   	RegionLabeling.setVerbose(BEVERBOSE);
		return DOES_8G + NO_CHANGES;
    }
	
    public void run(ImageProcessor ip) {
		RegionLabeling labeling;
		// choose any of 4 different labeling types:
//		labeling = new BreadthFirstLabeling(ip);
		labeling = new DepthFirstLabeling(ip);
//		labeling = new RecursiveLabeling(ip);	// works only for small images!
//		labeling = new SequentialLabeling(ip);

		labeling.printSummary();
		
		// show the resulting labeling as a random color image
		ImageProcessor labelIp = RECOLOR ?
					labeling.makeRandomColorImage() :
					labeling.makeGrayImage();		
		ImagePlus labelIm = new ImagePlus(origTitle + "-labeling", labelIp);
		labelIm.show();
		
		// get a list of regions to process
		List<BinaryRegion> regions = labeling.getRegions();
		for (BinaryRegion r : regions) {
			double mu11 = mu_11(labeling, r);
			IJ.log("Region " + r.getLabel() + ": mu11=" + mu11);
		}
    }

    
    /*
     * This method demonstrates how a particular region's central moment
     * mu_11 could be calculated from the finished region labeling.
     */
    double mu_11 (RegionLabeling labeling, BinaryRegion r) {
    	int label = r.getLabel();
    	Rectangle bb = r.getBoundingBox();
    	double xc = r.getCenter().x;	// centroid of this region
    	double yc = r.getCenter().y;
    	double s11 = 0;	// x/y sums
    	// collect all coordinates with exactly this label
    	for (int v=bb.y; v<bb.y+bb.height; v++) {
    		for (int u=bb.x; u<bb.x+bb.width; u++) {
    			if (labeling.getLabel(u,v) == label) {
    				s11 = s11 + (u - xc) * (v - yc);
    			}
    		}
    	}
    	return s11;
    }
    
}



