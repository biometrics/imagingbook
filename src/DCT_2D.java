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

import dct.*;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

/** Computes the 2-dimensional DCT on a float image
 * of arbitrary size. Be patient, this is quite slow!
 */

public class DCT_2D implements PlugInFilter{

	static boolean center = false;    //center the resulting spectrum?
	
	public int setup(String arg, ImagePlus im) {
		return DOES_8G+NO_CHANGES;
	}

	public void run(ImageProcessor ip) {
		FloatProcessor ip2 = (FloatProcessor) ip.convertToFloat();
		Dct2d dct = new Dct2d(ip2, center);
		
		ImageProcessor ipP = dct.makePowerImage();
		ImagePlus win = new ImagePlus("DCT Power Spectrum (byte)",ipP);
		win.show();
	}

}