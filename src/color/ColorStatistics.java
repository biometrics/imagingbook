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

package color;
import ij.process.ColorProcessor;

import java.util.Arrays;

public class ColorStatistics {
	
	//determine how many different colors are contained in the 24 bit full-color image cp
	public static int countColors (ColorProcessor cp) { 
		// duplicate pixel array and sort
		int[] pixels = ((int[]) cp.getPixels()).clone();
		Arrays.sort(pixels);  
		
		int k = 1;	// image contains at least one color
		for (int i = 0; i < pixels.length-1; i++) {
			if (pixels[i] != pixels[i+1])
				k = k + 1;
		}
		return k;
	}
	
	//computes the combined color histogram for color components (c1,c2)
	static int[][] get2dHistogram (ColorProcessor cp, int c1, int c2) 
	{	// c1, c2:  R = 0, G = 1, B = 2
		int[] RGB = new int[3];
		int[][] H = new int[256][256];	// histogram array H[c1][c2] 

		for (int v = 0; v < cp.getHeight(); v++) {
			for (int u = 0; u < cp.getWidth(); u++) {
				cp.getPixel(u, v, RGB); 
				int i = RGB[c1];	
				int j = RGB[c2];	
				// increment corresponding histogram cell
				H[j][i]++; // i runs horizontal, j runs vertical
			}
		}	
		return H;
	}
	

}
