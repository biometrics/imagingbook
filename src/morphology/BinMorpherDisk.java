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

package morphology;

public class BinMorpherDisk extends BinMorpher {
	
	BinMorpherDisk(){
		makeDisk(1);
	}
	
	public BinMorpherDisk(double radius) {
		makeDisk(radius);
		
	}
	
	private void makeDisk(double radius){
		int r = (int) Math.rint(radius);
		if (r <= 1) r = 1;
		int size = r + r + 1;
		se = new int[size][size];
		double r2 = radius * radius;

		for (int v=-r; v<=r; v++){
			for (int u=-r; u<=r; u++){
				if (u*u+v*v <= r2)
					se[v+r][u+r] = 1;
			}
		}
	}
	
}
