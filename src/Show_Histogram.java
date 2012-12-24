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

import histogram2.HistogramPlot;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Show_Histogram implements PlugInFilter { 
	
	String title;
	
	public int setup(String arg0, ImagePlus im) {
		title = im.getTitle();
		return DOES_8G + NO_CHANGES;
	}
	
	public void run(ImageProcessor ip) {
		HistogramPlot.showHistogram(ip, "Histogram of " + title);
		HistogramPlot.showCumHistogram(ip, "Cum. Histogram of " + title);
	}
	
}

