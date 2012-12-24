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

public class Equalize_Plugin implements PlugInFilter {

	public int setup(String arg, ImagePlus img) {
		return DOES_8G;
	}
    
	public void run(ImageProcessor ip) {
		int w = ip.getWidth();
		int h = ip.getHeight();
		int M = w * h; // total number of image pixels
		int K = 256; // number of intensity values

		// compute the cumulative histogram:
		int[] H = ip.getHistogram();
		for (int j = 1; j < H.length; j++) {
			H[j] = H[j - 1] + H[j];
		}

		// equalize the image:
		for (int v = 0; v < h; v++) {
			for (int u = 0; u < w; u++) {
				int a = ip.get(u, v);
				int b = H[a] * (K - 1) / M;
				ip.set(u, v, b);
			}
		}
	}
	
}