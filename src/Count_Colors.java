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

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

public class Count_Colors implements PlugInFilter {

	public void run(ImageProcessor ip) {
		int n = color.ColorStatistics.countColors((ColorProcessor) ip);
		IJ.write("This image has " + n + " colors.");
	}

	public int setup(String arg, ImagePlus imp) {
		return DOES_RGB + NO_CHANGES;
	}
}
