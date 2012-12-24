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

import ij.process.ImageProcessor;


public abstract class PixelInterpolator {
	ImageProcessor ip;
	
	PixelInterpolator() {}
	
	public void setImageProcessor(ImageProcessor ip) {
		this.ip = ip;
	}
	
	public abstract double getInterpolatedPixel(Point2D pnt);
}
