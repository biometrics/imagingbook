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

import Jama.Matrix;

/*
 * This version that uses the JAMA numerical math package 
 * (http://math.nist.gov/javanumerics/jama/) 
 * instead of JAMPACK (May 2007)
 */

public class BilinearMapping extends Mapping { 
	double a1, a2, a3, a4;
	double b1, b2, b3, b4;
	
	BilinearMapping(
					double a1, double a2, double a3, double a4,
					double b1, double b2, double b3, double b4, 
					boolean inv) {
		this.a1 = a1;   this.a2 = a2;   this.a3 = a3;   this.a4 = a4;
		this.b1 = b1;   this.b2 = b2;   this.b3 = b3;   this.b4 = b4;	
		isInverse = inv;			
	}
	
	//map between arbitrary quadrilaterals
	public static BilinearMapping makeInverseMapping(
			Point2D P1, Point2D P2, Point2D P3, Point2D P4,	// source quad
			Point2D Q1, Point2D Q2, Point2D Q3, Point2D Q4)	// target quad
		{	
		//define column vectors X, Y
		Matrix X = 
			new Matrix(new double[][] {{Q1.getX()},{Q2.getX()},{Q3.getX()},{Q4.getX()}});
		Matrix Y = 
			new Matrix(new double[][] {{Q1.getY()},{Q2.getY()},{Q3.getY()},{Q4.getY()}});
		
		//define matrix M
		Matrix M = new Matrix(new double[][]
			{{P1.getX(), P1.getY(), P1.getX() * P1.getY(), 1},
			 {P2.getX(), P2.getY(), P2.getX() * P2.getY(), 1},
			 {P3.getX(), P3.getY(), P3.getX() * P3.getY(), 1},
			 {P4.getX(), P4.getY(), P4.getX() * P4.getY(), 1}}
			);

		Matrix A = M.solve(X);		// solve M * A = X (A is unknown)
		Matrix B = M.solve(Y);		// solve M * B = Y (B is unknown)
		
		double a1 = A.get(0,0);		double b1 = B.get(0,0);
		double a2 = A.get(1,0);		double b2 = B.get(1,0);
		double a3 = A.get(2,0);		double b3 = B.get(2,0);
		double a4 = A.get(3,0);		double b4 = B.get(3,0);
		   
		return new BilinearMapping(a1,a2,a3,a4,b1,b2,b3,b4,true);
	}
						
	Point2D applyTo (Point2D pnt){
		double x0 = pnt.getX();
		double y0 = pnt.getY();
		double x1 = a1 * x0 + a2 * y0 + a3 * x0 * y0 + a4;
		double y1 = b1 * x0 + b2 * y0 + b3 * x0 * y0 + b4;
		pnt.setLocation(x1, y1);
		return pnt;
	}	
	

}