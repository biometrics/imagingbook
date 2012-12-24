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
import histogram2.PiecewiseLinearCdf;
import histogram2.Util;
import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Match_To_Piecewise_Linear_Histogram implements PlugInFilter { 
	
	public int setup(String arg0, ImagePlus im) {
		return DOES_8G;
	}
	
	public void run(ImageProcessor ipA) {
		HistogramPlot.showHistogram(ipA, "Histogram A");
		HistogramPlot.showCumHistogram(ipA, "Cumulative Histogram A");

		// get histogram of original image
		int[] hA = ipA.getHistogram();
		
		// -------------------------
		int[] ik = {28, 75, 150, 210};
		double[] Pk = {.05, .25, .75, .95};
		PiecewiseLinearCdf pLCdf =
			new PiecewiseLinearCdf(256, ik, Pk);
		// -------------------------
		
		double[] nhB = pLCdf.getPdf();
		nhB = Util.normalizeHistogram(nhB);
		(new HistogramPlot(nhB, "Piecewise Linear")).show();
		(new HistogramPlot(pLCdf, "Piecewise Linear Cumulative")).show();
		
		HistogramMatcher m = new HistogramMatcher();
		int[] F = m.matchHistograms(hA, pLCdf);
		
		for (int i=0; i<F.length; i++) {
			IJ.write(i + " -> " + F[i]);
		}
		
		ipA.applyTable(F);
		HistogramPlot.showHistogram(ipA, "Histogram A (mod)");
		HistogramPlot.showCumHistogram(ipA, "Cumulative Histogram A (mod)");
	}

}

