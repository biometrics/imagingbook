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

package dft2;
import ij.process.*;

public class Dft2d {
	int width;
	int height;
	float[] Real;	//original image data
	float[] Imag;
	float[] Power;
	float PowerMax;
	boolean swapQu = true;
	int scaleValue = 255;	
	boolean forward = true;
	
	public Dft2d(FloatProcessor ip){
		width = ip.getWidth();
		height = ip.getHeight();
		Real = (float[]) ip.getPixels();
		Imag = new float[width*height];  // values are zero
		doDft2d();
		makePowerSpectrum();
	}
	
	public Dft2d(FloatProcessor ip, boolean center){
		this(ip);
		swapQu = center;
	}
	
	//------------------------------------------------
	
	public void setForward(){
		forward = true;
	}
	
	public void setInverse(){
		forward = false;
	}
	
	public float[] getReal(){
		return Real;
	}
	
	public float[] getImag(){
		return Imag;
	}
	
	public float[] getPower(){
		return Power;
	}
	
	//------------------------------------------------
	
	public void doDft2d () { // in-place 2D Dft
		// do the rows:
		Complex[] row = Complex.makeComplexVector(width);
		Dft1d dftR = new Dft1d(width);
		for (int v=0; v<height; v++){
			getRow(v,row);
			Complex[] rowDft = dftR.DFT(row,forward);
			putRow(v,rowDft);
		}
		// do the columns:
		Complex[] col = Complex.makeComplexVector(height);
		Dft1d dftC = new Dft1d(height);
		for (int u=0; u<width; u++){
			getCol(u,col);
			Complex[] colDft = dftC.DFT(col,forward);
			putCol(u,colDft);
		}
	}
	
	void getRow(int v, Complex[] rowC){
		int i = v*width; //start index of row v
		for (int u=0; u<width; u++){
			rowC[u].re = Real[i+u];
			rowC[u].im = Imag[i+u];
		}
	}
	
	void putRow(int v, Complex[] rowC){
		int i = v*width; //start index of row v
		for (int u=0; u<width; u++){
			Real[i+u] = (float) rowC[u].re;
			Imag[i+u] = (float) rowC[u].im;
		}
	}
	
	void getCol(int u, Complex[] rowC){
		for (int v=0; v<height; v++){
			rowC[v].re = Real[v*width+u];
			rowC[v].im = Imag[v*width+u];
		}
	}
	
	void putCol(int u, Complex[] rowC){
		for (int v=0; v<height; v++){
			Real[v*width+u] = (float) rowC[v].re;
			Imag[v*width+u] = (float) rowC[v].im;
		}
	}
	
	//----------------------------------------------------
	
	void makePowerSpectrum(){
		//computes the power spectrum 
		Power = new float[Real.length];
		PowerMax = 0.0f;
		for (int i=0; i<Real.length; i++){
			double a = Real[i];
			double b = Imag[i];
			float p = (float) Math.sqrt(a*a + b*b);
			Power[i] = p;
			if (p>PowerMax)
				PowerMax = p;
		}
	}
	
	public ByteProcessor makePowerImage(){
		ByteProcessor ip = new ByteProcessor(width,height);
		byte[] pixels = (byte[]) ip.getPixels();
		//PowerMax must be set
		double max = Math.log(PowerMax+1.0);
		double scale = 1.0;
		if (scaleValue > 0)
			scale = scaleValue/max;
		for (int i=0; i<pixels.length; i++){
			double p = Power[i];
			if (p<0) p = -p;
			double plog = Math.log(p+1.0);
			int pint = (int)(plog * scale);
			pixels[i] = (byte) (0xFF & pint);
		}
		if (swapQu) swapQuadrants(ip);
		return ip;
	}

	//	----------------------------------------------------

	public void swapQuadrants (ImageProcessor ip) {
		//swap quadrants Q1 <-> Q3, Q2 <-> Q4
		// Q2 Q1
		// Q3 Q4
		ImageProcessor t1, t2;
		int w = ip.getWidth();
		int h = ip.getHeight();
		int w2 = w/2;
		int h2 = h/2;
		
		ip.setRoi(w2,0,w-w2,h2); // Q1
		t1 = ip.crop();
		
		ip.setRoi(0,h2,w2,h-h2); //Q3
		t2 = ip.crop();
		
		ip.insert(t1,0,h2); //swap Q1 <-> Q3
		ip.insert(t2,w2,0);
		
		ip.setRoi(0,0,w2,h2); //Q2
		t1 = ip.crop();
		
		ip.setRoi(w2,h2,w-w2,h-h2); //Q4
		t2 = ip.crop();
		
		ip.insert(t1,w2,h2);
		ip.insert(t2,0,0);
	}

}


