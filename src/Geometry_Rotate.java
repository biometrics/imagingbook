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
import mappings.Rotation;

public class Geometry_Rotate implements PlugInFilter {
	static double angle = 15; // rotation angle (in degrees)

    public int setup(String arg, ImagePlus imp) {
        return DOES_8G;
    }

    public void run(ImageProcessor ip) {
		Rotation map = new Rotation((2 * Math.PI * angle) / 360);
		PixelInterpolator ipol;
		ipol = new BicubicInterpolator(0.5);  // Catmul-Rom interpolator a = 0.5
		//ipol = new BilinearInterpolator();
		//ipol = new NearestNeighborInterpolator();
		map.applyTo(ip, ipol);
    }
}
