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
import mappings.RippleMapping;
import interpolators.*;

public class Geometry_RippleMapping implements PlugInFilter {

    public int setup(String arg, ImagePlus imp) {
        return DOES_8G;
    }

    public void run(ImageProcessor ip) {
		double xWavel = 120;
		double xAmpl = 10;
		double yWavel = 250;
		double yAmpl = 10;

		RippleMapping map = RippleMapping.makeInverseMapping(xWavel,xAmpl,yWavel,yAmpl);
		
		PixelInterpolator ipol;
		ipol = new BicubicInterpolator();
		//ipol = new BilinearInterpolator();
		//ipol = new NearestNeighborInterpolator();
		map.applyTo((ByteProcessor)ip, ipol);
    }

}
