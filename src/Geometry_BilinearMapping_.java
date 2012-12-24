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

import ij.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;
import interpolators.*;
import mappings.*;

public class Geometry_BilinearMapping_ implements PlugInFilter {

    public int setup(String arg, ImagePlus imp) {
        return DOES_8G;
    }

    public void run(ImageProcessor ip) {
		Point2D p1 = new Point(0,0);
		Point2D p2 = new Point(400,0);
		Point2D p3 = new Point(400,400);
		Point2D p4 = new Point(0,400);
		
		Point2D q1 = new Point(0,60);
		Point2D q2 = new Point(400,20);
		Point2D q3 = new Point(300,400);
		Point2D q4 = new Point(30,200);
		
		
		BilinearMapping map = BilinearMapping.makeInverseMapping(q1,q2,q3,q4,p1,p2,p3,p4);
		
//		p1 = map.applyTo(p1);
//		p1.printIJ("a1");	
//		p2 = map.applyTo(p2);
//		p2.printIJ("a2");	
//		p3 = map.applyTo(p3);
//		p3.printIJ("a3");	
//		p4 = map.applyTo(p4);
//		p4.printIJ("a4");	
		
		PixelInterpolator ipol;
		ipol = new BicubicInterpolator();
		//ipol = new BilinearInterpolator();
		//ipol = new NearestNeighborInterpolator();
		map.applyTo((ByteProcessor)ip, ipol);
    }


}
