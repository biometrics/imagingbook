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

package mappings;

import java.awt.geom.Point2D;

public class RippleMapping extends Mapping {
	double xWavel = 20;
	double yWavel = 100;
	double xAmpl = 0;
	double yAmpl = 10;
   
	RippleMapping (
			double xWavel, double xAmpl, 
			double yWavel, double yAmpl, 
			boolean inv) {
		this.xWavel = xWavel / (2 * Math.PI);
		this.yWavel = yWavel / (2 * Math.PI);
		this.xAmpl = xAmpl;
		this.yAmpl = yAmpl;
		this.isInverse = inv;
	}
	
	public static RippleMapping makeInverseMapping(
			double xWavel, double xAmpl, double yWavel, double yAmpl){
		return new RippleMapping(xWavel, xAmpl, yWavel, yAmpl, true);
	}

	Point2D applyTo (Point2D pnt){
		double x0 = pnt.getX();
		double y0 = pnt.getY();	
		double x1 = x0 + xAmpl * Math.sin(y0 / xWavel);
		double y1 = y0 + yAmpl * Math.sin(x0 / yWavel);
		pnt.setLocation(x1, y1);
		return pnt;
	}
}




