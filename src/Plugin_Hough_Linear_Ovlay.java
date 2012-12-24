/**
 * This sample code is made available as part of the book "Digital Image
 * Processing - An Algorithmic Introduction using Java" by Wilhelm Burger
 * and Mark J. Burge, Copyright (C) 2005-2008 Springer-Verlag Berlin, 
 * Heidelberg, New York.
 * Note that this code comes with absolutely no warranty of any kind.
 * See http://www.imagingbook.com for details and licensing conditions.
 * 
 * Date: 2010/01/27
 */

import hough.LinearHT;
import hough.LinearHT.HoughLine;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.GeneralPath;
import java.util.List;

/** 
 * This plugin implements a simple Hough Transform for straight lines.
 * It expects an 8-bit binary (edge) image, with background = 0 and
 * edge pixels > 0.
 * Draws the resulting lines as a non-destructive vector overlay.
 * Last update: 2010-07-24
*/

public class Plugin_Hough_Linear_Ovlay implements PlugInFilter {
	
	static int N_ANGLE = 256;			// resolution of angle
	static int N_RADIUS = 256;			// resolution of radius
	static int MAX_LINES = 10;			// max. number of lines to be detected
	static int MIN_PNTSONLINE = 150;	// min. number of points on each line
	
	static boolean SHOW_ACCUMULATOR = false;
	static boolean SHOW_LOCALMAXIMA = false;
	static boolean LIST_LINES = false;
	static boolean DRAW_LINES = true;
	static int LINE_WIDTH = 3;
	static Color LINE_COLOR = Color.GREEN;

	ImagePlus imp = null;		// input image

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_8G + DOES_16 + NO_CHANGES;
	}
	
	boolean askForParameters() {
		GenericDialog gd = new GenericDialog("Linear Hough Transform");
		gd.addNumericField("Angular steps", N_ANGLE, 0);
		gd.addNumericField("Radial steps", N_RADIUS, 0);
		gd.addNumericField("Number of lines", MAX_LINES, 0);
		gd.addNumericField("Min. points/line", MIN_PNTSONLINE, 0);
		gd.addCheckbox("Show accumulator", SHOW_ACCUMULATOR);
		gd.addCheckbox("Show local maxima", SHOW_LOCALMAXIMA);
		gd.addCheckbox("List lines", LIST_LINES);
		gd.addCheckbox("Draw lines", DRAW_LINES);
		gd.showDialog();
		if(gd.wasCanceled()) 
			return false;
		N_ANGLE = (int) gd.getNextNumber();
		N_RADIUS = (int) gd.getNextNumber();
		MAX_LINES = (int) gd.getNextNumber();
		MIN_PNTSONLINE = (int) gd.getNextNumber();
		SHOW_ACCUMULATOR = gd.getNextBoolean();
		SHOW_LOCALMAXIMA = gd.getNextBoolean();
		LIST_LINES = gd.getNextBoolean();
		DRAW_LINES = gd.getNextBoolean();	
		N_ANGLE = Math.max(N_ANGLE, 10);
		N_RADIUS  = Math.max(N_RADIUS, 10);
		MAX_LINES = Math.max(MAX_LINES, 1);
		MIN_PNTSONLINE = Math.max(MIN_PNTSONLINE, 1);
		return true;
	}

	public void run(ImageProcessor ip) {
		// ip is supposed to be an edge image with background = 0
		
		if (askForParameters() == false) 
			return;

		//compute the Hough Transform
		LinearHT ht = new LinearHT(ip, N_ANGLE, N_RADIUS);
		List<HoughLine> lines = ht.getMaxLines(MAX_LINES, MIN_PNTSONLINE);

		// show the Hough accumulator
		if (SHOW_ACCUMULATOR) {
			FloatProcessor accIp = ht.getAccumulatorImage();
			accIp.flipHorizontal(); //flip because angle runs reverse (negative y)
			ImagePlus accImg = new ImagePlus("HT of " + imp.getTitle(), accIp);
			accImg.show();
		}
		
		// show the local maxima of the Hough accumulator
		if (SHOW_LOCALMAXIMA) {
			FloatProcessor maxIp = ht.getLocalMaxImage();
			maxIp.flipHorizontal(); //flip because angle runs reverse (negative y)
			ImagePlus maxIm = new ImagePlus("Local maxima of " + imp.getTitle(), maxIp);
			maxIm.show();
		}
		
		// list the line parameters on the console
		if (LIST_LINES) {
			printLines(lines);
		}
		
		// plot the found lines as non-destructive vector overlay
		if (DRAW_LINES) {
			drawLines(lines, imp);
		}
	}

	void printLines(List<HoughLine> lines) {
		int i = 0;
		for (HoughLine hl : lines){
			i = i+1;
			IJ.log(i + ": " + hl.toString());
		}
	}
	
	void drawLines(List<HoughLine> lines, ImagePlus resultIm) {
		GeneralPath path = new GeneralPath();
		for (HoughLine hl : lines){
			path.append(hl.makeLine2D(), false);
		}
		resultIm.setOverlay(path, LINE_COLOR, new BasicStroke(LINE_WIDTH));
	}
	
}

