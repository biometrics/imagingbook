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

package regions;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

/*
 * This class is used to incrementally compute and maintain
 * the statistics of a binary region.
 */

public class BinaryRegion {
	int label;
	int numberOfPixels = 0;
	double xc = Double.NaN;
	double yc = Double.NaN;
	int left = Integer.MAX_VALUE;
	int right = -1;
	int top = Integer.MAX_VALUE;
	int bottom = -1;
	
	// auxiliary variables
	int x_sum  = 0;
	int y_sum  = 0;
	int x2_sum = 0;
	int y2_sum = 0;
	
	// ------- constructor --------------------------

	public BinaryRegion(int id){
		this.label = id;
	}
	
	// ------- public methods --------------------------
	
	public int getLabel() {
		return this.label;
	}
	
	public int getSize() {
		return this.numberOfPixels;
	}
	
	public Rectangle getBoundingBox() {
		if (left == Integer.MAX_VALUE) 
			return null;
		else
			return new Rectangle(left, top, right-left+1, bottom-top+1);
	}
	
	public Point2D.Double getCenter(){
		if (Double.isNaN(xc))
			return null;
		else
			return new Point2D.Double(xc, yc);
	}
	
	/* Use this method to add a single pixel to this region. Updates summation
	 * and boundary variables used to calculate various region statistics.
	 */
	public void addPixel(int x, int y){
		numberOfPixels = numberOfPixels + 1;
		x_sum = x_sum + x;
		y_sum = y_sum + y;
		x2_sum = x2_sum + x*x;
		y2_sum = y2_sum + y*y;
		if (x<left) left = x;
		if (y<top)  top = y;
		if (x>right) right = x;
		if (y>bottom) bottom = y;
	}
	
	/* Call this method to update the region's statistics. For now only the 
	 * center coordinates (xc, yc) are updated. Add additional statements as
	 * needed to update your own region statistics.
	 */
	public void update(){
		if (numberOfPixels > 0){
			xc = (double) x_sum / numberOfPixels;
			yc = (double) y_sum / numberOfPixels;
		}
	}
	
	public String toString(){
		return
			"Region: " + label +
			" / pixels: " + numberOfPixels +
			" / bbox: (" + left + "," + top + "," + right + "," + bottom + ")" +
			" / center: (" + trunc(xc,2) + "," + trunc(yc,2) + ")"
			;
	}
	
	// --------- local auxiliary methods -------------------
	
	String trunc(double d){
		long k = Math.round(d * 100);
		return String.valueOf(k/100.0);
	}
	
	String trunc(double d, int precision){
		double m =  Math.pow(10,precision);
		long k = Math.round(d * m);
		return String.valueOf(k/m);
	}

}
