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

public class Gamma_Correction implements PlugInFilter {

	public int setup(String arg, ImagePlus img) {
		return DOES_8G;
	}
    
	public void run(ImageProcessor ip) {
		// works for 8-bit images only 
	    int K = 256;
	    int aMax = K - 1;
	    double GAMMA = 2.8;   
	
	    // create a lookup table for the mapping function
	    int[] Fgc = new int[K];                
	
	    for (int a = 0; a < K; a++) {
	        double aa = (double) a / aMax;	// scale to $[0,1]$
	        double bb = Math.pow(aa,GAMMA);	// gamma function \indexmeth{pow}
	        // scale back to [0,255]
	        int b = (int) Math.round(bb * aMax); 
	        Fgc[a] = b;  
	    }
	    
	    ip.applyTable(Fgc);  // modify the image
	}	
}