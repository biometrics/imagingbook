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

public class Brighten_Rgb_1 implements PlugInFilter {

	public void run(ImageProcessor ip) {
		int[] pixels = (int[]) ip.getPixels();  

		for (int i = 0; i < pixels.length; i++) { 
			int c = pixels[i];	                   
			// split color pixel into rgb-components
			int r = (c & 0xff0000) >> 16;          
			int g = (c & 0x00ff00) >> 8; 
			int b = (c & 0x0000ff);
			// modify colors
			r = r + 10; if (r > 255) r = 255;  
			g = g + 10; if (g > 255) g = 255;
			b = b + 10; if (b > 255) b = 255;
			// reassemble color pixel and insert into pixel array
			pixels[i] = ((r & 0xff)<<16) | ((g & 0xff)<<8) | b & 0xff; 
		}
	}

	public int setup(String arg, ImagePlus imp) {
		return DOES_RGB;	// this plugin works on RGB images 
	}
}
