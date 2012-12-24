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
import ij.process.ImageProcessor;

public class Raise_Contrast implements PlugInFilter {

	public int setup(String arg, ImagePlus img) {
		return DOES_8G;
	}
    
	public void run(ImageProcessor ip) {
		int w = ip.getWidth();
		int h = ip.getHeight();

		for (int v = 0; v < h; v++) {
			for (int u = 0; u < w; u++) {
				int a = (int) (ip.get(u, v) * 1.5 + 0.5);
				if (a > 255)
					a = 255; 		// clamp to max. value
				ip.set(u, v, a);
			}
		}
	}
	
}