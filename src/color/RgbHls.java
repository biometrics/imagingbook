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

/** Methods for converting between RGB and HLS color spaces.
*/

class RgbHls {

	static float[] RGBtoHLS (float R, float G, float B) {
		// R,G,B assumed to be in [0,1]
		float cHi = Math.max(R,Math.max(G,B));	// highest color value
		float cLo = Math.min(R,Math.min(G,B));	// lowest color value
		float cRng = cHi - cLo;				    // color range
		
		// compute lightness L
		float L = (cHi + cLo)/2;
		
		// compute saturation S		
		float S = 0;
		if (0 < L && L < 1) {
			float d = (L <= 0.5f) ? L : (1 - L);
			S = 0.5f * cRng / d;
		}

		// compute hue H
		float H=0;
		if (cHi > 0 && cRng > 0) {        // a color pixel
			float rr = (float)(cHi - R) / cRng;
			float gg = (float)(cHi - G) / cRng;
			float bb = (float)(cHi - B) / cRng;
			float hh;
			if (R == cHi)                      // r is highest color value
				hh = bb - gg;
			else if (G == cHi)                 // g is highest color value
				hh = rr - bb + 2.0f;
			else                               // b is highest color value
				hh = gg - rr + 4.0f;
			
			if (hh < 0)
			  hh= hh + 6;
			H = hh / 6;
		}

		return new float[] {H,L,S};
	}
	
	
	static float[] HLStoRGB (float H, float L, float S) {
		// H,L,S assumed to be in [0,1]
		float R = 0, G = 0, B = 0;
	
		if (L <= 0)				// black
			R = G = B = 0;
		else if (L >= 1)		// white
			R = G = B = 1;
		else {
			float hh = (6 * H) % 6;
			int   c1 = (int) hh;
			float c2 = hh - c1;
			float d = (L <= 0.5f) ? (S * L) : (S * (1 - L));
			float w = L + d;
			float x = L - d;
			float y = w - (w - x) * c2;
			float z = x + (w - x) * c2;
			switch (c1) {
				case 0: R=w; G=z; B=x; break;
				case 1: R=y; G=w; B=x; break;
				case 2: R=x; G=w; B=z; break;
				case 3: R=x; G=y; B=w; break;
				case 4: R=z; G=x; B=w; break;
				case 5: R=w; G=x; B=y; break;
			}			
		}
		return new float[] {R,G,B};
	}
	
}
