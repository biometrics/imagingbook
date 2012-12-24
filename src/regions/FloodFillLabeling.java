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

import ij.*;
import ij.process.*;

/*
 * This is the superclass for all flood-fill labeling classes.
 */

public abstract class FloodFillLabeling extends RegionLabeling {
	
	FloodFillLabeling (ImageProcessor ip) {
		super(ip);
	}

	/*
	 * applyLabeling() is the same for all flood-fill methods
	 */ 
	void applyLabeling() {
		if (beVerbose)
			IJ.log("applyLabeling()");
		resetLabel();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (isForeground(x, y)) {
					// start a new region
					int label = getNextLabel();
					if (beVerbose)
						IJ.log("Starting Flood-Fill at " + x + "," + y
								+ " Label = " + label);
					floodFill(x, y, label);
				}
			}
		}
	}
	
	/*
	 * This class is abstract and cannot be instantiated.
	 * Any real flood-fill class must implement this:
	 */
	abstract void floodFill(int x, int y, int label);

}
