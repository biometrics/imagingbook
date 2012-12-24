/**
 * This sample code is made available as part of the book "Digital Image
 * Processing - An Algorithmic Introduction using Java" by Wilhelm Burger
 * and Mark J. Burge, Copyright (C) 2005-2008 Springer-Verlag Berlin, 
 * Heidelberg, New York.
 * Note that this code comes with absolutely no warranty of any kind.
 * See http://www.imagingbook.com for details and licensing conditions.
 */

package harris;
import ij.process.ImageProcessor;

class Corner implements Comparable<Corner> {
	int u;
	int v;
	float q;

	Corner (int u, int v, float q) {
		this.u = u;
		this.v = v;
		this.q = q;
	}
    
	public int compareTo (Corner c2) {
		//used for sorting corners by corner strength q
		if (this.q > c2.q) return -1;
		if (this.q < c2.q) return 1;
		else return 0;
	}
	
	double dist2 (Corner c2){
		//returns the squared distance between this corner and corner c2
		int dx = this.u - c2.u;
		int dy = this.v - c2.v;
		return (dx*dx)+(dy*dy);	
	}
	
	void draw(ImageProcessor ip){
		//draw this corner as a black cross
		int paintvalue = 0; //black
		int size = 2;
		ip.setValue(paintvalue);
		ip.drawLine(u-size,v,u+size,v);
		ip.drawLine(u,v-size,u,v+size);
	}
}
