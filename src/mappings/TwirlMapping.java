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

public class TwirlMapping extends Mapping {
	double xc, yc, angle, rad;
   
	TwirlMapping (double xc, double yc, double angle, double rad, boolean inv) {
		this.xc = xc;
		this.yc = yc;
		this.angle = angle;
		this.rad = rad;
		this.isInverse = inv;
	}

	public static TwirlMapping makeInverseMapping(double xc, double yc, double angle, double rad){
		return new TwirlMapping(xc, yc, angle, rad, true);
	}

	Point2D applyTo (Point2D pnt){
		double x = pnt.getX();
		double y = pnt.getY();
		double dx = x - xc;
		double dy = y - yc;
		double d = Math.sqrt(dx*dx + dy*dy);
		if (d < rad) {
			double a = Math.atan2(dy,dx) + angle * (rad-d) / rad;
			double x1 = xc + d * Math.cos(a);
			double y1 = yc + d * Math.sin(a);
			pnt.setLocation(x1, y1);
		}
		return pnt;
	}
}




