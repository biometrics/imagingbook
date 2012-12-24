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

package dft1;

public class Complex {
	public double re;
	public double im;

	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	public static Complex[] makeComplexVector(int M) {
		Complex[] g = new Complex[M];
		for (int i = 0; i < M; i++) {
			g[i] = new Complex(0,0);
		}
		return g;
	}
	
	public static Complex[] makeComplexVector(double[] signal) {
		int M = signal.length;
		Complex[] g = new Complex[M];
		for (int i = 0; i < M; i++) {
			g[i] = new Complex(signal[i], 0);
		}
		return g;
	}
	
	static Complex[] makeComplexVector(double[] real, double[] imag) {
		int M = real.length;
		Complex[] g = new Complex[M];
		for (int i = 0; i < M; i++) {
			g[i] = new Complex(real[i], imag[i]);
		}
		return g;
	}

	public static void printComplexVector(Complex[] g, String title) {
		System.out.println("Printing " + title);
		for (int i = 0; i < g.length; i++) {
			if (g[i] == null)
				System.out.println(i + ": ******");
			else {
				double gr = g[i].re;
				double gi = g[i].im;
				gr = (Math.rint(gr * 1000) / 1000);
				gi = (Math.rint(gi * 1000) / 1000);
				if (gi >= 0)
					System.out.println(i + ": " + gr + " + " + Math.abs(gi) + "i");
				else
					System.out.println(i + ": " + gr + " - " + Math.abs(gi) + "i");
			}
		}
	}
}
