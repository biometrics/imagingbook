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


public class NearestNeighborInterpolator extends PixelInterpolator {
	
	public double getInterpolatedPixel(Point2D pnt) {
		int u = (int) Math.rint(pnt.getX());
		int v = (int) Math.rint(pnt.getY());
		return ip.getPixel(u,v);
	}

	
}
