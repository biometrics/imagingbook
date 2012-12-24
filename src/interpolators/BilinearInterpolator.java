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

public class BilinearInterpolator extends PixelInterpolator {

	public double getInterpolatedPixel(Point2D pnt) {
		double x = pnt.getX();
		double y = pnt.getY();
		int u = (int) Math.floor(x);
		int v = (int) Math.floor(y);
		double a = x - u;
		double b = y - v;
		int A = ip.getPixel(u,v);
		int B = ip.getPixel(u+1,v);
		int C = ip.getPixel(u,v+1);
		int D = ip.getPixel(u+1,v+1);
		double E = A + a*(B-A);
		double F = C + a*(D-C);
		double G = E + b*(F-E);
		return G;
	}
	
}
