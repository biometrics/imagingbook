/**
 * This sample code is made available as part of the book "Digital Image
 * Processing - An Algorithmic Introduction using Java" by Wilhelm Burger
 * and Mark J. Burge, Copyright (C) 2005-2008 Springer-Verlag Berlin, 
 * Heidelberg, New York.
 * Note that this code comes with absolutely no warranty of any kind.
 * See http://www.imagingbook.com for details and licensing conditions.
 */

import harris.HarrisCornerDetector;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/*
 * This plugin implements the Harris corner detector.
 * Date: 2007/11/10
 * Modified: 2010/07/06
*/

public class Harris_Corner_Detector implements PlugInFilter {
	ImagePlus im;
	static float alpha = HarrisCornerDetector.DEFAULT_ALPHA;
	static int threshold = HarrisCornerDetector.DEFAULT_THRESHOLD;
	static int nmax = 0;	//points to show
	static boolean doCleanUp = true;

    public int setup(String arg, ImagePlus im) {
    	this.im = im;
        return DOES_8G + NO_CHANGES;
    }
    
    public void run(ImageProcessor ip) {
		if (!showDialog()) return; //dialog canceled or error
		HarrisCornerDetector hcd = new HarrisCornerDetector(ip,alpha,threshold);
		hcd.setDoCleanup(doCleanUp);
		hcd.findCorners();
		ImageProcessor result = hcd.showCornerPoints(ip, nmax);
		ImagePlus win = new ImagePlus("Corners from " + im.getTitle(),result); 
		win.show();
    }
    
	private boolean showDialog() {
		// display dialog , return false if canceled or on error.
		GenericDialog dlg = new GenericDialog("Harris Corner Detector", IJ.getInstance());
		float def_alpha = HarrisCornerDetector.DEFAULT_ALPHA;
		dlg.addNumericField("Alpha (default: "+def_alpha+")", alpha, 3);
		int def_threshold = HarrisCornerDetector.DEFAULT_THRESHOLD;
		dlg.addNumericField("Threshold (default: "+def_threshold+")", threshold, 0);
		dlg.addNumericField("Corners to show (0 = show all)", nmax, 0);
		dlg.addCheckbox("Clean up corners", doCleanUp);
		dlg.showDialog();
		if(dlg.wasCanceled())
			return false;
		if(dlg.invalidNumber()) {
			IJ.showMessage("Error", "Invalid input number");
			return false;
		}		
		alpha = (float) dlg.getNextNumber();
		threshold = (int) dlg.getNextNumber();
		nmax = (int) dlg.getNextNumber();
		doCleanUp = dlg.getNextBoolean();
		return true;
	}
}
