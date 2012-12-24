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

package histogram1;
import ij.process.ImageProcessor;

public class HistogramBinning {

	int[] binnedHistogram(ImageProcessor ip) {
		int K = 256; // number of intensity values
		int B = 32; // size of histogram, must be defined
		int[] H = new int[B]; // histogram array
		int w = ip.getWidth();
		int h = ip.getHeight();

		for (int v = 0; v < h; v++) {
			for (int u = 0; u < w; u++) {
				int a = ip.getPixel(u, v);
				int i = a * B / K; // integer operations only! 
				H[i] = H[i] + 1;
			}
		}
		// return binned histogram 
		return H;
	}
}