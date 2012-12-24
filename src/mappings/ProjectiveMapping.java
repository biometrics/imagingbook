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

public class ProjectiveMapping extends LinearMapping { 
	
	ProjectiveMapping(
		double a11, double a12,	double a13,
		double a21,	double a22,	double a23,
		double a31,	double a32, boolean inv) {
		super(a11,a12,a13,a21,a22,a23,a31,a32,1,inv);
	}
	
	// creates the projective mapping from the unit square S to
	// the arbitrary quadrilateral Q given by points P1 ... P4:
	public static ProjectiveMapping makeMapping(Point2D A1, Point2D A2, Point2D A3, Point2D A4) {
		double x1 = A1.getX(), x2 = A2.getX(), x3 = A3.getX(), x4 = A4.getX(); 
		double y1 = A1.getY(), y2 = A2.getY(), y3 = A3.getY(), y4 = A4.getY();
		double S = (x2-x3)*(y4-y3) - (x4-x3)*(y2-y3);
		
		double a31 = ((x1-x2+x3-x4)*(y4-y3)-(y1-y2+y3-y4)*(x4-x3)) / S;
		double a32 = ((y1-y2+y3-y4)*(x2-x3)-(x1-x2+x3-x4)*(y2-y3)) / S;
		double a11 = x2 - x1 + a31*x2;
		double a12 = x4 - x1 + a32*x4;
		double a13 = x1;
		double a21 = y2 - y1 + a31*y2;
		double a22 = y4 - y1 + a32*y4;
		double a23 = y1;
		return new ProjectiveMapping(a11,a12,a13,a21,a22,a23,a31,a32,false);
	}
	
	// creates the projective mapping between arbitrary quadrilaterals Qa, Qb
	// via the unit square: Qa -> S -> Qb
	public static ProjectiveMapping makeMapping (
			Point2D A1, Point2D A2, Point2D A3, Point2D A4, 
			Point2D B1, Point2D B2, Point2D B3, Point2D B4)	{
		ProjectiveMapping T1 = makeMapping(A1, A2, A3, A4);
		ProjectiveMapping T2 = makeMapping(B1, B2, B3, B4);
		LinearMapping T1i = (LinearMapping) T1.invert();
		LinearMapping T = T1i.concat(T2);
		T.isInverse = false;
		return (ProjectiveMapping) T;
	}
	
/*	
	ProjectiveMapping(Point2D B1, Point2D B2, Point2D B3, Point2D B4) {
		double x1 = B1.x, x2 = B2.x, x3 = B3.x, x4 = B4.x; 
		double y1 = B1.y, y2 = B2.y, y3 = B3.y, y4 = B4.y;
		double S = (x2-x3)*(y4-y3) - (x4-x3)*(y2-y3);
		
		a31 = ((x1-x2+x3-x4)*(y4-y3) - (y1-y2+y3-y4)*(x4-x3)) / S;
		a32 = ((y1-y2+y3-y4)*(x2-x3) - (x1-x2+x3-x4)*(y2-y3)) / S;
		a11 = x2 - x1 + a31*x2;
		a12 = x4 - x1 + a32*x4;
		a13 = x1;
		a21 = y2 - y1 + a31*y2;
		a22 = y4 - y1 + a32*y4;
		a23 = y1;
	}
*/

}