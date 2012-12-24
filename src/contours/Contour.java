/**
 * This sample code is made available as part of the book "Digital Image
 * Processing - An Algorithmic Introduction using Java" by Wilhelm Burger
 * and Mark J. Burge, Copyright (C) 2005-2008 Springer-Verlag Berlin, 
 * Heidelberg, New York.
 * Note that this code comes with absolutely no warranty of any kind.
 * See http://www.imagingbook.com for details and licensing conditions.
 * 
 * Date: 2010/08/01
 */

package contours;
import ij.IJ;

/*
 * 2006-01-01 made Java 5.0 compliant (generic ArrayList, Iterator)
 */

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Contour {
	
	static int INITIAL_SIZE = 50;
	
	int label;
	List<Point> points;
	
	Contour (int label, int size) {
		this.label = label;
		points = new ArrayList<Point>(size);
	}
	
	Contour (int label) {
		this.label = label;
		points = new ArrayList<Point>(INITIAL_SIZE);
	}
	
	void addPoint (Point n){
		points.add(n);
	}
	
	//--------------------- drawing ------------	
	
	Shape makePolygon() {
		int m = points.size();
		if (m>1){
			int[] xPoints = new int[m];
			int[] yPoints = new int[m];
			int k = 0;
			Iterator<Point> itr = points.iterator();
			while (itr.hasNext() && k < m) {
				Point cpt = itr.next();
				xPoints[k] = cpt.x;
				yPoints[k] = cpt.y;
				k = k + 1;
			}
			return new Polygon(xPoints, yPoints, m);
		}
		else {	// use circles for isolated pixels
			Point cpt = points.get(0);
			return new Ellipse2D.Double(cpt.x-0.1, cpt.y-0.1, 0.2, 0.2);
		}
	}


	static Shape[] makePolygons(List<Contour> contours) {
		if (contours == null) 
			return null;
		else {
			Shape[] pa = new Shape[contours.size()];
			int i = 0;
			for (Contour c: contours) {
				pa[i] = c.makePolygon();
				i = i + 1;
			}
			return pa;
		}
	}

	void moveBy (int dx, int dy) {
		for (Point pt: points) {
			pt.translate(dx,dy);
		}
	}
	
	static void moveContoursBy (List<Contour> contours, int dx, int dy) {
		for (Contour c: contours) {
			c.moveBy(dx, dy);
		}	
	}

	//--------------------- chain code ------------	

	/*
	byte[] makeChainCode8() {
		int m = points.size();
		if (m>1){
			int[] xPoints = new int[m];
			int[] yPoints = new int[m];
			int k = 0;
			Iterator<Point> itr = points.iterator();
			while (itr.hasNext() && k < m) {
				Point cn = itr.next();
				xPoints[k] = cn.x;
				yPoints[k] = cn.y;
				k = k + 1;
			}
			return null;
		}
		else {	// use circles for isolated pixels
			//Point cn = 
				points.get(0);
			return null;
		}
	}
	*/
		
	//--------------------- contour statistics ------------
	
	public int getLength() {
		return points.size();
	}
	
	//--------------------- debug methods ------------------
	
	public void printPoints (){
		for (Point pt: points) {
			IJ.write("" + pt);
		}
	}
	
	public String toString(){
		return
			"Contour " + label + ": " + this.getLength() + " points";
	}

}