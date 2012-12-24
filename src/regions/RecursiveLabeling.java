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
import ij.process.*;

public class RecursiveLabeling extends FloodFillLabeling {

	public RecursiveLabeling(ImageProcessor ip) {
		super(ip);
	}

	public void floodFill(int u, int v, int label) {
		if ((u>=0) && (u<width) && (v>=0) && (v<height) && isForeground(u,v)) {
			setLabel(u, v, label);
			floodFill(u+1,v,label);
			floodFill(u,v+1,label);
			floodFill(u,v-1,label);
			floodFill(u-1,v,label);
		}
	}

}
