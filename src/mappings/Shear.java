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

package mappings;

class Shear extends AffineMapping {

	Shear(double bx, double by) {
		super( // calls constructor of AffineMapping
			1,  bx, 0,
			by, 1,  0, 
			false);
	}
	
}


