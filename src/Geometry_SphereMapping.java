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
import mappings.SphereMapping;
import interpolators.*;

public class Geometry_SphereMapping implements PlugInFilter {

    public int setup(String arg, ImagePlus imp) {
        return DOES_8G;
    }

    public void run(ImageProcessor ip) {
    	int w = ip.getWidth();
    	int h = ip.getHeight();
		
		SphereMapping map = SphereMapping.makeInverseMapping(w/2+10, h/2, h/2);
		
		PixelInterpolator ipol;
		ipol = new BicubicInterpolator();
		//ipol = new BilinearInterpolator();
		//ipol = new NearestNeighborInterpolator();
		map.applyTo((ByteProcessor)ip, ipol);
    }

}
