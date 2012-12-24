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

public class ColorHistogram {
	int colorArray[] = null;
	int countArray[] = null;
	
	ColorHistogram(int[] color, int[] count) {
		this.countArray = count;
		this.colorArray = color;
	}
	
	public ColorHistogram(ColorProcessor ip) {
		this((int[]) ip.getPixels());
	}
	
	ColorHistogram(int[] pixelsOrig) {
		int N = pixelsOrig.length;
		int[] pixelsCpy = new int[N];
		for (int i = 0; i < N; i++) {
			// remove possible alpha components
			pixelsCpy[i] = 0xFFFFFF & pixelsOrig[i];
		}
		Arrays.sort(pixelsCpy);
		
		// count unique colors:
		int k = -1; // current color index
		int curColor = -1;
		for (int i = 0; i < pixelsCpy.length; i++) {
			if (pixelsCpy[i] != curColor) {
				k++;
				curColor = pixelsCpy[i];
			}
		}
		int nColors = k+1;
		
		// tabulate and count unique colors:
		colorArray = new int[nColors];
		countArray = new int[nColors];
		k = -1;	// current color index
		curColor = -1;
		for (int i = 0; i < pixelsCpy.length; i++) {
			if (pixelsCpy[i] != curColor) {	// new color
				k++;
				curColor = pixelsCpy[i];
				colorArray[k] = curColor;
				countArray[k] = 1;
			}
			else {
				countArray[k]++;
			}
		}
	}
	
	public int[] getColorArray() {
		return colorArray;
	}
	
	public int[] getCountArray() {
		return countArray;
	}
	
	public int getNumberOfColors() {
		if (colorArray == null)
			return 0;
		else
			return colorArray.length;
	}
	
	public int getColor(int index) {
		return this.colorArray[index];
	}
	
	public int getCount(int index) {
		return this.countArray[index];
	}
}
