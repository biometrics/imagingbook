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

package regions;
import ij.IJ;
import ij.process.ImageProcessor;
import java.util.HashMap;
import java.util.Map;

/*
 * 2006-01-01 made Java 5.0 compliant (generic Map, List)
 * 2007-05-28 moved Collission class locally
 * 2008-02-28 fixed bug for empty replacement tables (caused by empty images)
 */


public class SequentialLabeling extends RegionLabeling {

	//Hashtable<Collission,Collission> map = null;
	Map<Collision,Collision> collissionMap;

	public SequentialLabeling(ImageProcessor ip) {
		super(ip);
	}

	void applyLabeling() {
		if (beVerbose) IJ.log("Sequential region labeling - Step 1");
		//map = new Hashtable<Collission,Collission>(1000);
		collissionMap = new HashMap<Collision,Collision>(1000);
		
		// Step 1: asign initial labels:
		resetLabel();
		for (int v = 0; v < height; v++) {
			for (int u = 0; u < width; u++) {
				if (isForeground(u, v)) {
					setLabel(u, v, makeLabel(u, v));
				}
			}
		}
		
		// Step 2: resolve label collisions
		if (beVerbose) IJ.log("Sequential region labeling - Step 2");
		int[] replacementTable = makeReplacementTable();
		
		// Step 3: relabel the image
		applyReplacementTable(replacementTable);
		//showLabelArray();
	}

	int makeLabel(int u, int v) {
		int newLabel = 0;
		//assemble the neighborhood n:
		//
		// [1][2][3]
		// [0][x]
		//
		int[] n = new int[4];
		n[0] = getLabel(u - 1, v);
		//gives 0 if no label is set or outside borders
		n[1] = getLabel(u-1, v-1);
		n[2] = getLabel(u, v-1);
		n[3] = getLabel(u+1, v-1);

		if (   n[0] == BACKGROUND
			&& n[1] == BACKGROUND
			&& n[2] == BACKGROUND
			&& n[3] == BACKGROUND) {
			//all neigbors in n[] are empty, assign a new label:
			newLabel = this.getNextLabel(); 		
		} 
		else { //at least one label in n[] is not BACKGROUND
			//find minimum region label among neighbors
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < 4; i++) {
				int ni = n[i];
				if (ni >= START_LABEL && ni < min)
					min = ni;
			}
			newLabel = min;

			//register label equivalence (collission)
			for (int i = 0; i < 4; i++) {
				int ni = n[i];
				if (ni >= START_LABEL && ni != newLabel) {
					registerCollission(ni, newLabel);
				}
			}
		}
		return newLabel;
	}

	void registerCollission(int a, int b) {
		if (collissionMap==null){
			throw new Error("registerCollission(): no collission map!");
		}
		if (a != b) {
			Collision c;
			if (a < b)
				c = new Collision(a, b);
			else
				c = new Collision(b, a);
			if (!collissionMap.containsKey(c))
				collissionMap.put(c, c);
		}
	}
	
	void listCollissions() {
		IJ.log("Listing collissions********* " + collissionMap.size());
		for (Collision c: collissionMap.keySet()) {
			IJ.log("  ---next----" + c.a + "/" + c.b + "=" + c);
		}
	}
	
	//---------------------------------------------------------------------------

	int[] makeReplacementTable() {
		int size = getMaxLabel() + 1;
		int[] rTable = resolveCollisions(size);
		return cleanupReplacementTable(rTable);
	}
	
	int[] resolveCollisions(int size) {
		/* This is the core of the algorithm: 
		The set of collissions (stored in map) is used to merge connected regions.
		Transitivity of collissions makes this a nontrivial task. The algorithm 
		used here is a basic "Connected-Components Algorithm" as used for finding
		connected parts in undirected graphs (e.g. see Corman, Leiserson, Rivest: 
		"Introduction to Algorithms", MIT Press, 1995, p. 441).
		Here, the regions represent the nodes of the graph while the collissions
		are equivalent to the edges of the graph.
		The implementation is not particularly efficient, since the merging of sets 
		is done by relabelling the entire replacement table for each pair of nodes.
		Still fast enough even for large and complex images.
		*/

		// The table setNumber[i] indicates to which set the element i belongs:
		//   k == setNumber[e] means that e is in set k 
		int[] setNumber = new int[size];

		//Initially, we assign a separate set to each element e:
		for (int e = 0; e < size; e++) {
			setNumber[e] = e;
		}

		// Inspect all collissions c=(a,b) one by one [note that a<b]
		for (Collision c: collissionMap.keySet()) {
			int A = setNumber[c.a]; //element a is currently in set A
			int B = setNumber[c.b]; //element b is currently in set B
			//Merge sets A and B (unless they are the same) by
			//moving all elements of set B into set A
			if (A != B) {
				for (int i = 0; i < size; i++) {
					if (setNumber[i] == B)
						setNumber[i] = A;
				}
			}
		}
		return setNumber;
	}

	int[] cleanupReplacementTable(int[] table) {
		if (table.length == 0) return table; // case of empty image
		// Assume the replacement table looks the following:
		// table = [0 1 4 4 4 6 6 8 3 3 ]
		//     i =  0 1 2 3 4 5 6 7 8 9
		// meaning that label 2 should be replaced by 4 etc.
		
		// First, figure out which of the original labels
		// are still used. For this we use an intermediate array "mark":
		int[] mark = new int[table.length];
		for (int i = 0; i < table.length; i++) {
			int k = table[i];
			if (k >= 0 && k < table.length)
				mark[k] = 1;
		}
		// Result:
		// mark = [1 1 0 1 1 0 1 0 1 0 ]
		//    i =  0 1 2 3 4 5 6 7 8 9
		
		// Now we assign new, contiguous labels in mark:
		int newLabel = START_LABEL;
		mark[BACKGROUND] = BACKGROUND;
		mark[FOREGROUND] = FOREGROUND;
		for (int i = START_LABEL; i < table.length; i++) {
			if (mark[i]>0) {
				mark[i] = newLabel;
				newLabel = newLabel + 1;
			}
		}
		// Result:
		// mark = [0 1 0 2 3 0 4 0 5 0 ]
		//    i =  0 1 2 3 4 5 6 7 8 9
		
		//Now modify the actual replacement table to reflect the new labels
		for (int i = 0; i < table.length; i++) {
			table[i] = mark[table[i]];
		}
        // table = [0 1 4 4 4 6 6 8 3 3 ]
        //              |             |
        //              V             V
		// table = [0 1 3 3 3 4 4 5 2 2 ]
		//     i =  0 1 2 3 4 5 6 7 8 9
		
		return table;
	}

	void applyReplacementTable(int[] replacementTable){
		if (replacementTable != null && replacementTable.length > 0){
			for (int i=0; i<labels.length; i++){
				int old = labels[i];
				if (old>=START_LABEL && old<replacementTable.length){	
					labels[i] = replacementTable[old];
				}
			}
		}
	}
	
	//---------------------------------------------------------------------
	
	/*
	 * This class represents a collision between two pixel labels a, b
	 */
	
	class Collision { 
		int a, b;

		Collision(int label_a, int label_b) {
			a = label_a;
			b = label_b;
		}

		// This is important because hashCode determines how keys in the hashtable are
		// compared. It makes sure that the key is directly related to the values of a,b. 
		// Otherwise the key would be derived from the address of the Collision object.
		public int hashCode() {
			return a * b;
		}

		public boolean equals(Object obj) {
			if (obj instanceof Collision) {
				Collision c = (Collision) obj;
				return (this.a == c.a && this.b == c.b);
			} else
				return false;
		}

	}

}


