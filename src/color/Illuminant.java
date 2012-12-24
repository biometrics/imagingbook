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

public enum Illuminant {
	E	(1/3.0, 1/3.0),						// 5400K, Equal energy
	D50	(0.964296, 1.00000, 0.825100),		// 5000K
//	D50	(0.34773, 0.35952 ),				// 5000K
	D55 (0.33411, 0.34877),					// 5500K
	D65 (0.31382, 0.33100),					// 6500K, Television, sRGB color space
//	D65 (0.950456, 1.00000, 1.088754),		// 6500K, Television, sRGB color space
	D75 (0.29968, 0.31740),					// 7500K
	A	(0.45117, 0.40594),					// 2856K, Incandescent tungsten
	B	(0.3498, 0.3527),					// 4874K, Obsolete, direct sunlight at noon 
	C	(0.31039, 0.31905),					// 6774K, Obsolete, north sky daylight 
	F2	(0.37928, 0.36723),					// 4200K, Cool White Fluorescent (CWF)
	F7	(0.31565, 0.32951),					// 6500K,  Broad-Band Daylight Fluorescent 
	F11	(0.38543, 0.37110)					// 4000K,  Narrow Band White Fluorescent 
	;
	
	public final double X, Y, Z;

	private Illuminant(double X, double Y, double Z) {
		this.X = X; this.Y = Y; this.Z = Z;
	}
	
	private Illuminant(double x, double y) {
		Y = 1.0;
		X = x * Y / y; 
		Z = (1.0 - x - y) * Y / y;
	}
	
	public float[] getXyzFloat() {
		return new float[] {(float)X, (float)Y, (float)Z};
	}
	
}
