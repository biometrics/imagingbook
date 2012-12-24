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
import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Match_To_Image_Histogram implements PlugInFilter { 
	
	ImagePlus imB;	// reference image (selected interactively)
	
	public int setup(String arg0, ImagePlus imA) {
		return DOES_8G;
	}
	
	public void run(ImageProcessor ipA) {
		if (!runDialog()) // select the reference image
			return;
		
		ImageProcessor ipB = imB.getProcessor();
		HistogramPlot.showHistogram(ipA, "Histogram A");
		HistogramPlot.showHistogram(ipB, "Histogram B");
		HistogramPlot.showCumHistogram(ipA, "Cumulative Histogram A");
		HistogramPlot.showCumHistogram(ipB, "Cumulative Histogram B");
		// get histograms of both images
		int[] hA = ipA.getHistogram();
		int[] hB = ipB.getHistogram();
		
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

	boolean runDialog() {
		// get list of open images
		int[] windowList = WindowManager.getIDList();
		if(windowList==null){
			IJ.noImage();
			return false;
		}
		// get image titles
		String[] windowTitles = new String[windowList.length];
		for (int i = 0; i < windowList.length; i++) {
			ImagePlus imp = WindowManager.getImage(windowList[i]);
			if (imp != null)
				windowTitles[i] = imp.getShortTitle();
			else
				windowTitles[i] = "untitled";
		}
		// create dialog and show
		GenericDialog gd = new GenericDialog("Select Reference Image");
		gd.addChoice("Reference Image:", windowTitles, windowTitles[0]);
		gd.showDialog(); 
		if (gd.wasCanceled()) 
			return false;
		else {
			int img2Index = gd.getNextChoiceIndex();
			imB = WindowManager.getImage(windowList[img2Index]);
			return true;
		}
	}

}

