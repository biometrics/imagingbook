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

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import interpolators.BicubicInterpolator;
import interpolators.PixelInterpolator;

import java.awt.Point;
import java.awt.geom.Point2D;

import mappings.ProjectiveMapping;

public class Geometry_ProjectiveMapping implements PlugInFilter {

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
		
		ProjectiveMapping map = ProjectiveMapping.makeMapping(p1,p2,p3,p4,q1,q2,q3,q4);
		PixelInterpolator ipol;
		ipol = new BicubicInterpolator();
		//ipol = new BilinearInterpolator();
		//ipol = new NearestNeighborInterpolator();

		map.applyTo(ip, ipol);
    }
}
