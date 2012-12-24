/**
 * This sample code is made available as part of the book "Digital Image
 * Processing - An Algorithmic Introduction using Java" by Wilhelm Burger
 * and Mark J. Burge, Copyright (C) 2005-2008 Springer-Verlag Berlin, 
 * Heidelberg, New York.
 * Note that this code comes with absolutely no warranty of any kind.
 * See http://www.imagingbook.com for details and licensing conditions.
 * 
 * Date: 2010/08/01
 */

package regions;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ColorProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/* 
 * This class does the complete region labeling for a given image.
 * It is abstract, because the implementation of some parts depend
 * upon the particular region labeling algorithm being used.
 */

public abstract class RegionLabeling {
	
	static boolean doDisplay = false;
	static boolean beVerbose = false;

	final int BACKGROUND = 0;
	final int FOREGROUND = 1;
	final int START_LABEL = 2;
	int[] labels = null;
	int width;
	int height;
	private int currentLabel;
	private int maxLabel;	// the maximum label in the labels array
	List<BinaryRegion> regions = null;

	RegionLabeling(ImageProcessor ip) {
		width  = ip.getWidth();
		height = ip.getHeight();
		makeLabelArray(ip);
		//showLabelArray();
		if (labels == null)
			IJ.error("RegionLabeling: could not create labels array");
		applyLabeling();
		collectRegions();
	}
	
	void makeLabelArray(ImageProcessor ip) {
		if (beVerbose)
			IJ.log("makeLabelArray()");
		// set all pixels to FOREGROUND or BACKGROUND (thresholding)
		labels = new int[width * height];
		for (int v = 0; v < height; v++) {
			for (int u = 0; u < width; u++) {
				int p = ip.getPixel(u, v);
				if (p > 0)
					labels[v * width + u] = FOREGROUND;
				else
					labels[v * width + u] = BACKGROUND;
			}
		}
	}
	
	/* Must be implemented by the sub-classes:
	 */
	abstract void applyLabeling();
	
	/* Creates a container of BinaryRegion objects, then collects the regions'
	 * coordinates by scanning the "labels" array.
	 */
	void collectRegions() {
		if (beVerbose) IJ.log("makeRegions()");
		// create an array of BinaryRegion objects
		BinaryRegion[] regionArray = new BinaryRegion[maxLabel + 1];
		for (int i = START_LABEL; i <= maxLabel; i++) {
			regionArray[i] = new BinaryRegion(i);
		}
		// scan the labels array and collect the coordinates for each region
		for (int v = 0; v < height; v++) {
			for (int u = 0; u < width; u++) {
				int lb = labels[v * width + u];
				if (lb >= START_LABEL && lb <= maxLabel && regionArray[lb]!=null) {
					regionArray[lb].addPixel(u,v);
				}
			}
		}
		
		// collect all nonempty regions and create the final list of regions
		List<BinaryRegion> regionList = new ArrayList<BinaryRegion>();
		for (BinaryRegion r: regionArray) {
			if (r != null && r.getSize() > 0) {
				r.update();	// calculate the statistics for this region
				regionList.add(r);
			}
		}
		regions = regionList;
	}
	
	boolean isForeground(int u, int v) {
		return (labels[v * width + u] == FOREGROUND);
	}
	
	void resetLabel() {
		currentLabel = -1;
		maxLabel = -1;
	}
	
	int getNextLabel() {
		if (currentLabel < 1)
			currentLabel = START_LABEL;
		else
			currentLabel = currentLabel + 1;
		maxLabel = currentLabel;
		if (beVerbose) 
			IJ.log("  new label: "+ currentLabel + " / max label: " + maxLabel);
		return currentLabel;
	}
	
	void setLabel(int u, int v, int label) {
		if (u >= 0 && u < width && v >= 0 && v < height)
			labels[v * width + u] = label;
	}
	
	// public methods ------------------------------------

	public static void setDisplay(boolean display) {
		doDisplay = display;
	}
	
	public static void setVerbose(boolean verbose) {
		beVerbose = verbose;
	}
	
	public List<BinaryRegion> getRegions() {
		return regions;
	}
	
	public int getMaxLabel() {
		return maxLabel;
	}
	
	public int getLabel(int u, int v) {
		if (u >= 0 && u < width && v >= 0 && v < height)
			return labels[v * width + u];
		else
			return BACKGROUND;
	}
	
	public boolean isLabel(int u, int v) {
		return (labels[v * width + u] >= START_LABEL);
	}
	
	public ImageProcessor makeLabelImage(boolean color) {
		if (color)
			return makeRandomColorImage();
		else
			return makeGrayImage();
	}
	
	//	public ByteProcessor makeGrayImage() {
	//	ByteProcessor bp = new ByteProcessor(width, height);
	//	byte[] bytePix = (byte[]) bp.getPixels();
	//
	//	for (int i = 0; i < labels.length; i++) {
	//			bytePix[i] = (byte) (0xFF & labels[i]);
	//	}
	//	bp.invertLut();
	//	return bp;
	//}
	
	public FloatProcessor makeGrayImage() {
		FloatProcessor ip = new FloatProcessor(width, height, labels);
		ip.resetMinAndMax();
		return ip;
	}


	public ColorProcessor makeRandomColorImage() {
		int[] colorLUT = new int[maxLabel+1];

		for (int i = START_LABEL; i <= maxLabel; i++) {
			colorLUT[i] = makeRandomColor();
		}

		ColorProcessor cp = new ColorProcessor(width, height);
		int[] colPix = (int[]) cp.getPixels();

		for (int i = 0; i < labels.length; i++) {
			if (labels[i]>=0 && labels[i]<colorLUT.length)
				colPix[i] = colorLUT[labels[i]];
			else
				throw new Error("illegal label = "+labels[i]);
		}
		return cp;
	}
	
	public void printSummary() {
		if (regions != null && regions.size() > 0) {
			IJ.log("Summary: number of regions = " + regions.size());
			for (BinaryRegion r : regions) {
				IJ.log(r.toString());
			}
		} else
			IJ.log("Summary: no regions found.");
	}
	
	// local utility methods -----------------------------------
	
	void showLabelArray() {
		ImageProcessor ip = new FloatProcessor(width, height, labels);
		ip.resetMinAndMax();
		ImagePlus im = new ImagePlus("Label Array",ip);
		im.show();
	}

	int makeRandomColor() {
		double saturation = 0.2;
		double brightness = 0.2;
		float h = (float) Math.random();
		float s = (float) (saturation * Math.random() + 1 - saturation);
		float b = (float) (brightness * Math.random() + 1 - brightness);
		return Color.HSBtoRGB(h, s, b);
	}
	
	void snooze(int time) {
		if (time > 0) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
