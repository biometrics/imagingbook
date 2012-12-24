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

package matching;
import ij.process.FloatProcessor;

public class CorrCoeffMatcher {
	FloatProcessor I; // image
	FloatProcessor R; // template
	int wI, hI; 	// width/height of image
	int wR, hR; 	// width/height of template
	int K;

	float meanR; // mean value of template
	float varR;  // variance of template

	public CorrCoeffMatcher(FloatProcessor img, FloatProcessor ref) {
		I = img;
		R = ref;
		wI = I.getWidth();
		hI = I.getHeight();
		wR = R.getWidth();
		hR = R.getHeight();
		K = wR * hR;

		// compute mean and variance of template
		float sumR = 0;
		float sumR2 = 0;
		for (int j = 0; j < hR; j++) {
			for (int i = 0; i < wR; i++) {
				float aR = R.getf(i,j);
				sumR  += aR;
				sumR2 += aR * aR;
			}
		}
		meanR = sumR / K;
		varR = (float) Math.sqrt(sumR2 - K * meanR * meanR);
	}
	
	public FloatProcessor computeMatch(){
		FloatProcessor C = new FloatProcessor(wI-wR+1,hI-hR+1);
		
		for (int r=0; r<=wI-wR; r++){
			for (int s=0; s<=hI-hR; s++){
				float d = getMatchValue(r,s);
				C.setf(r,s,d);
			}	
		}
		return C;
	}
	
	float getMatchValue(int r, int s) {
		float sumI = 0, sumI2 = 0, sumIR = 0;
		
		for (int j=0; j<hR; j++){
			for (int i=0; i<wR; i++){
				float aI = I.getf(r+i,s+j);
				float aR = R.getf(i,j);
				sumI  += aI;
				sumI2 += aI * aI;
				sumIR += aI * aR;
			}
		}
		float meanI = sumI / K;
		return (sumIR - K * meanI * meanR) / 
			   ((float)Math.sqrt(sumI2 - K * meanI * meanI) * varR);
	}  
	
}
