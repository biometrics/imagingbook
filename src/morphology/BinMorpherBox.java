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

public class BinMorpherBox extends BinMorpher {
	
	BinMorpherBox(){
		makeBox();
	}
	
	private void makeBox(){
		se = new int[3][3];
		for (int v=0; v<3; v++){
			for (int u=0; u<3; u++){
					se[v][u] = 1;
			}
		}
	}
	
}
