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

package dct;

public class Dct1d{

	double[] gG;
	
	Dct1d(int M){ //Constructor
		gG = new double[M];	//this is done only ONCE when object is created!
	}
	
	public double[] DCT(double[] g) {
		int M = g.length;
		double s = Math.sqrt(2.0 / M); //common scale factor
		double[] G = gG;
		for (int m = 0; m < M; m++) {
			double cm;
			if (m == 0)
				cm = 1.0 / Math.sqrt(2);
			else
				cm = 1.0;
			double sum = 0;
			for (int u = 0; u < M; u++) {
				double Phi = (Math.PI * (2 * u + 1) * m) / (2.0 * M);
				sum += g[u] * cm * Math.cos(Phi);
			}
			G[m] = s * sum;
		}
		return G;
	}
	
	public double[] iDCT(double[] G) {
		int M = G.length;
		double s = Math.sqrt(2.0 / M); //common scale factor
		double[] g = gG;
		for (int u = 0; u < M; u++) {
			double sum = 0;
			for (int m = 0; m < M; m++) {
				double cm;
				if (m == 0) cm = 1.0/Math.sqrt(2);
				else		cm = 1.0;
				double Phi = (Math.PI * (2 * u + 1) * m) / (2.0 * M);
				double cosPhi = Math.cos(Phi);
				sum += cm * G[m] * cosPhi;
			}
			g[u] = s * sum;
		}
		return g;
	}
	
}