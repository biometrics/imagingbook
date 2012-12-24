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

public class Compute_Histogram implements PlugInFilter {

	public int setup(String arg, ImagePlus img) {
		return DOES_8G + NO_CHANGES; 
	}
    
	public void run(ImageProcessor ip) {
		int[] H = new int[256]; // histogram array
		int w = ip.getWidth();
		int h = ip.getHeight();

		for (int v = 0; v < h; v++) {
			for (int u = 0; u < w; u++) {
				int i = ip.getPixel(u, v);
				H[i] = H[i] + 1;
			}
		}

		// ... histogram H[] can now be used
	}
}