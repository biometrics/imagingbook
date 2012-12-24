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

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import colorQuantization.ColorQuantizer;
import colorQuantization.MedianCutQuantizer;


public class Median_Cut_Quantization implements PlugInFilter {
	static int NCOLORS = 32;
	
	public int setup(String arg, ImagePlus imp) {
		return DOES_RGB + NO_CHANGES;
	}
	
	public void run(ImageProcessor ip) {
		ColorProcessor cp = (ColorProcessor) ip.convertToRGB();
		
		// create a quantizer object
		ColorQuantizer quantizer = new MedianCutQuantizer(cp, NCOLORS);
		int qColors = quantizer.countQuantizedColors();
		
		// quantize to an indexed image
		ByteProcessor idxIp = quantizer.quantizeImage(cp);
		ImagePlus idxIm = new ImagePlus("Quantized Index Image (" + qColors + " colors)", idxIp);
		idxIm.show();
		
		// quantize to an RGB image
		int[] rgbPixels = quantizer.quantizeImage((int[]) cp.getPixels());
		ImageProcessor rgbIp = 
			new ColorProcessor(cp.getWidth(), cp.getHeight(), rgbPixels);
		ImagePlus rgbIm = 
			new ImagePlus("Quantized RGB Image (" + qColors + " colors)" , rgbIp);
		rgbIm.show();
		
	}
}
