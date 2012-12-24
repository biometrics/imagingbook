/**
 * This sample code is made available as part of the book "Digital Image
 * Processing - An Algorithmic Introduction using Java" by Wilhelm Burger
 * and Mark J. Burge, Copyright (C) 2005-2008 Springer-Verlag Berlin, 
 * Heidelberg, New York.
 * Note that this code comes with absolutely no warranty of any kind.
 * See http://www.imagingbook.com for details and licensing conditions.
 * 
 * Date: 2010/08/01
 */

package moments;
import ij.process.ImageProcessor;

public class Moments {
	static final int BACKGROUND = 0;

	public static double moment(ImageProcessor ip, int p, int q) {
		double Mpq = 0.0;
		for (int v = 0; v < ip.getHeight(); v++) { 
			for (int u = 0; u < ip.getWidth(); u++) { 
				if (ip.getPixel(u,v) != BACKGROUND) {
					Mpq+= Math.pow(u, p) * Math.pow(v, q);
				}
			}
		}
		return Mpq;
	}
	
	public static double centralMoment(ImageProcessor ip, int p, int q) {
		double m00  = moment(ip, 0, 0);	// region area
		double xCtr = moment(ip, 1, 0) / m00;
		double yCtr = moment(ip, 0, 1) / m00;
		double cMpq = 0.0;
		for (int v = 0; v < ip.getHeight(); v++) { 
			for (int u = 0; u < ip.getWidth(); u++) {
				if (ip.getPixel(u,v) != BACKGROUND) { 
					cMpq+= Math.pow(u - xCtr, p) * Math.pow(v - yCtr, q);
				}
			}
		}
		return cMpq;
	}
	
	public static double normalCentralMoment(ImageProcessor ip, int p, int q) {
		double m00 = moment(ip, 0, 0);
		double norm = Math.pow(m00, (double)(p + q + 2) / 2);
		return centralMoment(ip, p, q) / norm;
	}
}

