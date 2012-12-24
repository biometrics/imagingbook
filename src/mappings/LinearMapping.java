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

public class LinearMapping extends Mapping {
	double a11 = 1, a12 = 0, a13 = 0,
		   a21 = 0, a22 = 1, a23 = 0,
		   a31 = 0, a32 = 0, a33 = 1;
		   
	LinearMapping() {}
		   
	LinearMapping (double a11, double a12, double a13, 
				   double a21, double a22, double a23,
				   double a31, double a32, double a33, boolean inv) {
		this.a11 = a11;  this.a12 = a12;  this.a13 = a13;
		this.a21 = a21;  this.a22 = a22;  this.a23 = a23;
		this.a31 = a31;  this.a32 = a32;  this.a33 = a33;
		isInverse = inv;
	}
		   
	Point2D applyTo (Point2D pnt){
		double x0 = pnt.getX();
		double y0 = pnt.getY();
		double h = (a31*x0 + a32*y0 + a33);
		double x1 = (a11*x0 + a12*y0 + a13) / h;
		double y1 = (a21*x0 + a22*y0 + a23) / h;
		pnt.setLocation(x1, y1);
		return pnt;
	}
	
	Mapping invert(){
		LinearMapping lm = (LinearMapping) duplicate();	//works for all subclasses too
		double det = a11*a22*a33 + a12*a23*a31 + a13*a21*a32 - 
					 a11*a23*a32 - a12*a21*a33 - a13*a22*a31;
		lm.a11 = (a22*a33 - a23*a32) / det; 
		lm.a12 = (a13*a32 - a12*a33) / det; 
		lm.a13 = (a12*a23 - a13*a22) / det; 
		lm.a21 = (a23*a31 - a21*a33) / det; 
		lm.a22 = (a11*a33 - a13*a31) / det; 
		lm.a23 = (a13*a21 - a11*a23) / det;
		lm.a31 = (a21*a32 - a22*a31) / det; 
		lm.a32 = (a12*a31 - a11*a32) / det; 
		lm.a33 = (a11*a22 - a12*a21) / det;
		lm.isInverse = !isInverse;
		return lm;
	}
	
	
	// concatenates THIS transform matrix A with B: C = B*A
	LinearMapping concat(LinearMapping B){
		LinearMapping lm = (LinearMapping) duplicate();
		lm.a11 = B.a11*a11 + B.a12*a21 + B.a13*a31;
		lm.a12 = B.a11*a12 + B.a12*a22 + B.a13*a32;
		lm.a13 = B.a11*a13 + B.a12*a23 + B.a13*a33;
		
		lm.a21 = B.a21*a11 + B.a22*a21 + B.a23*a31;
		lm.a22 = B.a21*a12 + B.a22*a22 + B.a23*a32;
		lm.a23 = B.a21*a13 + B.a22*a23 + B.a23*a33;
		
		lm.a31 = B.a31*a11 + B.a32*a21 + B.a33*a31;
		lm.a32 = B.a31*a12 + B.a32*a22 + B.a33*a32;
		lm.a33 = B.a31*a13 + B.a32*a23 + B.a33*a33;
		return lm;
	}
	
	void print(String title) {
		System.out.println ("LinearMapping " + title);
		System.out.println ("  A1: " + a11 + " " + a12 + " " + a13);
		System.out.println ("  A2: " + a21 + " " + a22 + " " + a23);
		System.out.println ("  A3: " + a31 + " " + a32 + " " + a33);
	}
}




