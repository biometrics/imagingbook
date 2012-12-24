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

public class SphereMapping extends Mapping {
	double xc;				// center of sphere
	double yc;
	double rad;				// radius of sphere
	double refIdx = 1.8;	// refraction index
   
	SphereMapping (double xc, double yc, double rad, boolean inv) {
		this.xc = xc;
		this.yc = yc;
		this.rad = rad;
		this.isInverse = inv;
	}
	
	public static SphereMapping makeInverseMapping(double xc, double yc, double rad){
		return new SphereMapping(xc, yc, rad, true);
	}

	Point2D applyTo (Point2D pnt){ 
		double dx = pnt.getX()-xc;
		double dy = pnt.getY()-yc;
		double dx2 = dx*dx;
		double dy2 = dy*dy;
		double rad2 = rad*rad;
		
		double r2 = dx*dx + dy*dy;
		
		if (r2 > 0 && r2 < rad2) {
			double z2 = rad2 - r2; 
			double z = Math.sqrt(z2);

			double xAlpha = Math.asin(dx / Math.sqrt(dx2 + z2));
			double xBeta = xAlpha - xAlpha * (1 / refIdx);
			double x1 = pnt.getX() - z * Math.tan(xBeta);

			double yAlpha = Math.asin(dy / Math.sqrt(dy2 + z2));
			double yBeta = yAlpha - yAlpha * (1 / refIdx);
			double y1 = pnt.getY() - z * Math.tan(yBeta);
			pnt.setLocation(x1, y1);
		} 
		// otherwise leave point unchanged
		return pnt;
	}
}




