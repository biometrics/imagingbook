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

public class AffineMapping extends LinearMapping {
   
	AffineMapping (
			double a11, double a12, double a13, 
			double a21, double a22, double a23, 
			boolean inv) {
		super(a11,a12,a13,a21,a22,a23,0,0,1,inv);
	}
	
	// create the affine transform between arbitrary triangles A1..A3 and B1..B3
	public static AffineMapping makeMapping(
		Point2D A1, Point2D A2, Point2D A3,
		Point2D B1, Point2D B2, Point2D B3) {
			
		double ax1 = A1.getX(), ax2 = A2.getX(), ax3 = A3.getX();
		double ay1 = A1.getY(), ay2 = A2.getY(), ay3 = A3.getY();
		double bx1 = B1.getX(), bx2 = B2.getX(), bx3 = B3.getX();
		double by1 = B1.getY(), by2 = B2.getY(), by3 = B3.getY();
		
		double S = ax1*(ay3-ay2) + ax2*(ay1-ay3) + ax3*(ay2-ay1);
		double a11 = (ay1*(bx2-bx3) + ay2*(bx3-bx1) + ay3*(bx1-bx2)) / S;
		double a12 = (ax1*(bx3-bx2) + ax2*(bx1-bx3) + ax3*(bx2-bx1)) / S;
		double a21 = (ay1*(by2-by3) + ay2*(by3-by1) + ay3*(by1-by2)) / S;
		double a22 = (ax1*(by3-by2) + ax2*(by1-by3) + ax3*(by2-by1)) / S;
		double a13 = 
			(ax1*(ay3*bx2-ay2*bx3) + ax2*(ay1*bx3-ay3*bx1) + ax3*(ay2*bx1-ay1*bx2)) / S;
		double a23 = 
			(ax1*(ay3*by2-ay2*by3) + ax2*(ay1*by3-ay3*by1) + ax3*(ay2*by1-ay1*by2)) / S;
		
		return new AffineMapping(a11,a12,a13,a21,a22,a23,false);
	}

/*		   
	Pnt2d applyTo (Pnt2d pnt){
		double x = pnt.x, y = pnt.y;
		double xx = a11 * x + a12 * y + a13;
		double yy = a21 * x + a22 * y + a23;
		return new Pnt2d(xx, yy);
	}
	
	LinearMapping getInverse(){
		double r = a11*a22-a12*a21;
		double b11 = a22/r;
		double b12 = -a12/r;
		double b13 = (a12*a23 - a13*a22)/r;	
		double b21 = -a21/r;
		double b22 = a11/r;
		double b23 = (a13*a21 - a11*a23)/r;	
		return new AffineMapping(b11,b12,b13,b21,b22,b23);
	}
	*/
}




