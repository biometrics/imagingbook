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

package gauss;

public class GaussKernel1d {
	
	public static float[] create (double sigma) {
		return makeGaussKernel1d(sigma);
	}
	
	private static float[] makeGaussKernel1d(double sigma){
		// make 1D Gauss filter kernel large enough
		// to avoid truncation effects (too small in ImageJ!) 
		int center = (int) (3.0 * sigma);
		float[] kernel = new float[2 * center + 1]; // odd size
		double sigma2 = sigma * sigma;
		
		for (int i=0; i<kernel.length; i++){
			double r = center - i;
			kernel[i] =  (float) Math.exp(-0.5 * (r*r) / sigma2);
		}
		
		return kernel;
	}

}
