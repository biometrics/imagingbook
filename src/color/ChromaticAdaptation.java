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

public abstract class ChromaticAdaptation {
	
	protected float[] white1 = null;
	protected float[] white2 = null;

	// actual transformation of color coordinates.
	// XYZ1 are interpreted relative to white point W1.
	// Returns a new color adapted to white point W2.
	public abstract float[] apply (float[] XYZ1);
	
	protected ChromaticAdaptation() {
	}

	protected ChromaticAdaptation (float[] white1, float[] white2) {
		this.white1 = white1.clone();
		this.white2 = white2.clone();
	}
	
	protected ChromaticAdaptation(Illuminant illum1, Illuminant illum2) {
		this(illum1.getXyzFloat(), illum2.getXyzFloat());
	}

	public float[] getSourceWhite() {
		return white1;
	}
	
	public float[] getTargetWhite() {
		return white2;
	}

}
