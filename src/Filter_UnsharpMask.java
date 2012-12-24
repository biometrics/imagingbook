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

import gauss.GaussKernel1d;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.Convolver;
import ij.plugin.filter.PlugInFilter;
import ij.process.Blitter;
import ij.process.ImageProcessor;

/*
 * This plugin implements an Unsharp Mask filter similar to Photoshop 
 * without thresholds, using a "clean" (sufficiently large) Gaussian filter.
*/

public class Filter_UnsharpMask implements PlugInFilter {

	private static double radius = 1.0;	//radius = Gaussian sigma
	private static double amount = 100; //"amount" of sharpening in percent

	public int setup(String arg, ImagePlus imp) {
		if (arg.equals("about")) {
			showAbout();
			return DONE;
		}
		return DOES_8G;
	}
	
	public void run(ImageProcessor ip) {
		if (showDialog()){
			if (radius>20) radius = 20;
			if (radius<0.1) radius = 0.1;
			if (amount>500) amount = 500;
			if (amount<1) amount = 1;
			unsharpMask(ip, radius, amount/100);
		}
	}

	public void unsharpMask(ImageProcessor ip, double sigma, double a) {
		ImageProcessor I = ip.convertToFloat();
		
		//create a blurred version of the original
		ImageProcessor J = I.duplicate();
		float[] H = GaussKernel1d.create(sigma);
		Convolver cv = new Convolver(); 
		cv.setNormalize(true);
		cv.convolve(J,H,1,H.length);
		cv.convolve(J,H,H.length,1);
		
		//multiply the original image by (1+a)
		I.multiply(1+a);
		//multiply the mask image by a
		J.multiply(a);
		//subtract the weighted mask from the original
		I.copyBits(J,0,0,Blitter.SUBTRACT);

		//copy result back into original byte image
		ip.insert(I.convertToByte(false),0,0);
	}
	
	static void showProcessor(ImageProcessor ip, String title){
		ImagePlus win = new ImagePlus(title,ip);
		win.show();
	}
	
	public boolean showDialog() {
		GenericDialog gd = new GenericDialog("Unsharp Mask");
		gd.addNumericField("Radius = Sigma (0.1-20)", radius, 1);
		gd.addNumericField("Amount (1-500%)", amount, 0);
		gd.showDialog();
		if (gd.wasCanceled()) {
			return false;
		}
		//same limits as in Photoshop:
		radius = gd.getNextNumber();
		amount = gd.getNextNumber();
		return true;
	}
	
	void showAbout() {
		String cn = getClass().getName();
		IJ.showMessage("About " + cn + " ...", "Unsharp mask filter.");
	}
	
	void showStatus(String msg) {
		IJ.showStatus("Unsharp Mask: " + msg);
	}


}
