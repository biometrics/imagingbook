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

package contours;
import ij.ImagePlus;
import ij.gui.ImageCanvas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.util.List;

public class ContourOverlay extends ImageCanvas {
	private static final long serialVersionUID = 1L;
	static float strokeWidth = 0.5f; //0.2f;
	static int capsstyle = BasicStroke.CAP_ROUND;
	static int joinstyle = BasicStroke.JOIN_ROUND;
	static Color outerColor = Color.black;
	static Color innerColor = Color.white;
	static float[] outerDashing = {strokeWidth * 2.0f, strokeWidth * 2.5f}; 
	static float[] innerDashing = {strokeWidth * 0.5f, strokeWidth * 2.5f}; 
	static boolean DRAW_CONTOURS = true;
	
	Shape[] outerContourShapes = null;
	Shape[] innerContourShapes = null;
	
	public ContourOverlay
		(ImagePlus im, List<Contour> outerCs, List<Contour> innerCs) 
	{
		super(im);
		if (outerCs != null)
			outerContourShapes = Contour.makePolygons(outerCs);
		if (innerCs != null)
			innerContourShapes = Contour.makePolygons(innerCs);
	}

	public void paint(Graphics g) {
		super.paint(g);
		drawContours(g);
	}
	
	// non-public methods

	private void drawContours(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// scale and move overlay to the pixel centers
		double mag = this.getMagnification();
		g2d.scale(mag, mag);
		g2d.translate(0.5-this.srcRect.x, 0.5-this.srcRect.y);
		
		if (DRAW_CONTOURS) {
			Stroke solidStroke = 
				new BasicStroke(strokeWidth, capsstyle, joinstyle);
			Stroke dashedStrokeOuter = 
				new BasicStroke(strokeWidth, capsstyle, joinstyle, 1.0f, outerDashing, 0.0f);
			Stroke dashedStrokeInner = 
				new BasicStroke(strokeWidth, capsstyle, joinstyle, 1.0f, innerDashing, 0.0f);
	
			if (outerContourShapes != null)
				drawShapes(outerContourShapes, g2d, solidStroke, dashedStrokeOuter, outerColor);
			if (innerContourShapes != null)
				drawShapes(innerContourShapes, g2d, solidStroke, dashedStrokeInner, innerColor);
		}
	}
	
	void drawShapes(Shape[] shapes, Graphics2D g2d, Stroke solidStrk, Stroke dashedStrk, Color col) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(col);
		for (int i = 0; i < shapes.length; i++) {
			Shape s = shapes[i];
			if (s instanceof Polygon)
				g2d.setStroke(dashedStrk);
			else
				g2d.setStroke(solidStrk);
			g2d.draw(s);
		}	
	}

}



