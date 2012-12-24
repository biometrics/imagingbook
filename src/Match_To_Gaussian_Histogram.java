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

import histogram2.HistogramMatcher;
import histogram2.HistogramPlot;
import histogram2.Util;
import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Match_To_Gaussian_Histogram implements PlugInFilter { 
	
	public int setup(String arg0, ImagePlus im) {
		return DOES_8G;
	}
	
	public void run(ImageProcessor ipA) {
		HistogramPlot.showHistogram(ipA, "Histogram A");
		HistogramPlot.showCumHistogram(ipA, "Cumulative Histogram A");

		// get histogram
		int[] hA = ipA.getHistogram();
		int[] hB = Util.makeGaussianHistogram(128,50);
		
		double[] nhB = Util.normalizeHistogram(hB);
		(new HistogramPlot(nhB, "Gauss")).show();
		
		double[] chB = Util.Cdf(hB);
    	HistogramPlot hp = new HistogramPlot(chB, "Gauss cumulative");
		hp.show();
		
		HistogramMatcher m = new HistogramMatcher();
		int[] F = m.matchHistograms(hA, hB);
		
		for (int i=0; i<F.length; i++) {
			IJ.log(i + " -> " + F[i]);
		}
		
		ipA.applyTable(F);
		HistogramPlot.showHistogram(ipA, "Histogram A (mod)");
		HistogramPlot.showCumHistogram(ipA, "Cumulative Histogram A (mod)");
		
		//HistogramPlot hp = new HistogramPlot(chA);
		//hp.show();
		
	}

}

