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

import ij.*;
import ij.plugin.PlugIn;
import ij.process.ByteProcessor;

public class Geometry_MakeTestGrid implements PlugIn {
	
	static int w = 400;
	static int h = 400;
	static int xStep = 20;
	static int yStep = 20;
	static int xStart = 100;
	static int yStart = 100;
	static int xN = 10;
	static int yN = 10;
	
	int foreground = 0;
	int background = 255;
	
    public void run(String arg) {
    	ByteProcessor ip = new ByteProcessor(w,h);
    	ip.setValue(background);
    	ip.fill();
    	
		ip.setValue(foreground);
    	int y = yStart;
    	int x1 = xStart;
    	int x2 = xStart+xN*xStep;
    	for (int j = 0; j <= yN; j++) {
			ip.drawLine(x1,y,x2,y);
			y = y + yStep;
    	}
    	
		int x = xStart;
		int y1 = yStart;
		int y2 = yStart+yN*yStep;
		for (int i = 0; i <= xN; i++) {
			ip.drawLine(x,y1,x,y2);
			x = x + xStep;
		}
		
		ip.drawLine(0,0,w-1,h-1);
		ip.drawOval(xStart, yStart, 200, 200);
    	
        ImagePlus im = new ImagePlus("Grid",ip);
        im.show();
        im.updateAndDraw();
    }
    
 
 
}
