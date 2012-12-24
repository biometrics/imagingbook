/**
 * This sample code is made available as part of the book "Digital Image
 * Processing - An Algorithmic Introduction using Java" by Wilhelm Burger
 * and Mark J. Burge, Copyright (C) 2005-2008 Springer-Verlag Berlin, 
 * Heidelberg, New York.
 * Note that this code comes with absolutely no warranty of any kind.
 * See http://www.imagingbook.com for details and licensing conditions.
 * 
 * Date: 2007/11/10
 */

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

/*
 * This plugin demonstrates how to create and display a 
 * new byte image 
 */

public class Create_New_Image implements PlugInFilter {
	String title = null;

	public int setup(String arg, ImagePlus im) {
		title = im.getTitle();
		return DOES_8G + NO_CHANGES;
	}

	public void run(ImageProcessor ip) {
		int w = 256; 
		int h = 100; 
		int[] hist = ip.getHistogram();
		
		ImageProcessor histIp = new ByteProcessor(w, h);
		histIp.setValue(255);	// white = 255
		histIp.fill();

		// draw the histogram values as black bars in ip2 here, 
		// for example, using histIp.putpixel(u,v,0)
		// ...
		
		// display histogram:
		String hTitle = "Histogram of " + title;
		ImagePlus histIm = new ImagePlus(hTitle, histIp);
		histIm.show();
		// histIm.updateAndDraw();
	}
}