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
import ij.process.ImageProcessor;
import interpolators.PixelInterpolator;

import java.awt.geom.Point2D;


public abstract class Mapping implements Cloneable {
	boolean isInverse = false;
	
	// subclasses must implement this method
	abstract Point2D applyTo(Point2D pnt);
	
	Mapping invert() {
		throw new IllegalArgumentException("cannot invert mapping");
	}
	
	Mapping getInverse() {
		if (isInverse)
			return this;
		else 
			return invert(); // only linear mappings invert
	}
	
	// transforms the image in ip using this geometric mapping
	// and the specified pixel interpolator
	public void applyTo(ImageProcessor ip, PixelInterpolator intPol){
		ImageProcessor targetIp = ip;
		// make a temporary copy of the image:
		ImageProcessor sourceIp = ip.duplicate();
		
		Mapping invMap = this.getInverse(); // get inverse mapping 
		intPol.setImageProcessor(sourceIp);

		int w = targetIp.getWidth();
		int h = targetIp.getHeight();

		Point2D pt = new Point2D.Double();
		for (int v=0; v<h; v++){
			for (int u=0; u<w; u++){
				pt.setLocation(u,v);
				invMap.applyTo(pt);
				int p = (int) Math.rint(intPol.getInterpolatedPixel(pt));
				targetIp.putPixel(u,v,p);
			}
		}
	}
	
	Mapping duplicate() { //clones any mapping
		Mapping newMap = null;
		try {
			newMap = (Mapping) this.clone();
		}
		catch (CloneNotSupportedException e){};
		return newMap;
	}
	
}










