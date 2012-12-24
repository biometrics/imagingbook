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

import java.awt.Point;
import java.awt.geom.Point2D;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;
import mappings.*;
import interpolators.*;

public class Geometry_AffineMapping implements PlugInFilter {

    public int setup(String arg, ImagePlus imp) {
        return DOES_8G;
    }

    public void run(ImageProcessor ip) {


    	Point2D p1 = new Point(0,0);
    	Point2D p2 = new Point(400,0);
    	Point2D p3 = new Point(400,400);
		
    	Point2D q1 = new Point(0,60);
    	Point2D q2 = new Point(400,20);
    	Point2D q3 = new Point(300,400);
		
		AffineMapping map = AffineMapping.makeMapping(p1,p2,p3,q1,q2,q3);
		
		PixelInterpolator ipol;
		ipol = new BicubicInterpolator();
		//ipol = new BilinearInterpolator();
		//ipol = new NearestNeighborInterpolator();
		map.applyTo((ByteProcessor)ip, ipol);
    }
}
