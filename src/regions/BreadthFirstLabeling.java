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
import ij.process.ImageProcessor;

import java.awt.Point;
import java.util.LinkedList;

public class BreadthFirstLabeling extends FloodFillLabeling {
	
	static int LIMIT = Integer.MAX_VALUE;  
	
	public BreadthFirstLabeling(ImageProcessor ip) {
		super(ip);
	}

	void floodFill(int x, int y, int label) {
		int max_depth = 0;
		LinkedList<Point> q = new LinkedList<Point>();
		int ctr = 0; 
		q.addFirst(new Point(x, y));
		while (!q.isEmpty() && ctr<LIMIT) {
			int k = q.size();  	// for logging only - remove for efficiency!
			if (k>max_depth) max_depth = k;
			Point n = q.removeLast();
			int u = n.x;
			int v = n.y;
			if ((u>=0) && (u<width) && (v>=0) && (v<height) && isForeground(u,v)) {
				ctr ++;
				setLabel(u, v, label);
				q.addFirst(new Point(u + 1, v));
				q.addFirst(new Point(u, v + 1));
				q.addFirst(new Point(u, v - 1));
				q.addFirst(new Point(u - 1, v));
			}
		}
		//if (beVerbose) IJ.write("    max_depth:" + max_depth);
	}

}
