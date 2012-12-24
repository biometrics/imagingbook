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

public class XYZscalingAdaptation extends ChromaticAdaptation {

	public XYZscalingAdaptation(float[] white1, float[] white2) {
		super(white1, white2);
	}
	
	public XYZscalingAdaptation(Illuminant illum1, Illuminant illum2) {
		this(illum1.getXyzFloat(), illum2.getXyzFloat());
	}

	public float[] apply (float[] XYZ1) {
		float[] W1 = this.white1;
		float[] W2 = this.white2;
		float[] XYZ2 = new float[3];
		XYZ2[0] = XYZ1[0] * W2[0] / W1[0];
		XYZ2[1] = XYZ1[1] * W2[1] / W1[1];
		XYZ2[2] = XYZ1[2] * W2[2] / W1[2];
		return XYZ2;
	}

}
