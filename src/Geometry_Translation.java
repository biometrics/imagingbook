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

import ij.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;
import mappings.Translation;
import interpolators.*;

public class Geometry_Translation implements PlugInFilter {
	static double dx = 5.25;
	static double dy = 7.3;
	
    public int setup(String arg, ImagePlus imp) {
        return DOES_8G;
    }

    public void run(ImageProcessor ip) {

		Translation map = new Translation(dx,dy);
	
		PixelInterpolator ipol = new BicubicInterpolator();
		//ipol = new BilinearInterpolator();
		//ipol = new NearestNeighborInterpolator();
		//ipol = new BiSincInterpolator(ip,60);
		map.applyTo((ByteProcessor)ip, ipol);
    }

}
