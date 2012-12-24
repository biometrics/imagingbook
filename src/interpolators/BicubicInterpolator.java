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

package interpolators;
import java.awt.geom.Point2D;

public class BicubicInterpolator extends PixelInterpolator {
	double a = 1;
	
	public BicubicInterpolator() {}
	
	public BicubicInterpolator(double a){
		this.a = a;
	}
	
	public double getInterpolatedPixel(Point2D pnt) {
		double x0 = pnt.getX();
		double y0 = pnt.getY();
		int u0 = (int) Math.floor(x0);	//use floor to handle negative coordinates too
		int v0 = (int) Math.floor(y0);

		double q = 0;
		for (int j = 0; j <= 3; j++) {
			int v = v0 - 1 + j;
			double p = 0;
			for (int i = 0; i <= 3; i++) {
				int u = u0 - 1 + i;
				p = p + ip.getPixel(u,v) * cubic(x0 - u);
			}
			q = q + p * cubic(y0 - v);
		}
		return q;
	}
	
	double cubic(double x) {
		if (x < 0) x = -x;
		double z = 0;
		if (x < 1) 
			z = (-a+2)*x*x*x + (a-3)*x*x + 1;
		else if (x < 2) 
			z = -a*x*x*x + 5*a*x*x - 8*a*x + 4*a;
		return z;
	}	
}
