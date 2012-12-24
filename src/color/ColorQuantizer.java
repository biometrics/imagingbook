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

package color;

import ij.process.ByteProcessor;
import ij.process.ColorProcessor;

/* 
 * This abstract class is used a the superclass for color quantizers.
 */

public abstract class ColorQuantizer {
	
	public abstract ByteProcessor quantizeImage(ColorProcessor cp);
	public abstract int[] quantizeImage(int[] origPixels);
	public abstract int countQuantizedColors();

}
