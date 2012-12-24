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
import ij.*;
import ij.process.Blitter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

public class BinMorpher {
	
	int[][] se;	//structuring element

	public static enum Operation {
		Dilation, Erosion, Opening, Closing, Outline;
	}
	
	public static String[] getOpNames() {
		Operation[] ops = Operation.values();
		String[] strgs = new String[ops.length];
		int i = 0;
		for (Operation op: ops) {
			strgs[i++] = op.name();
		}
		return strgs;
	}


	// constructor methods
	
	BinMorpher(){
	}
	
	public BinMorpher(int[][] structuringElement){
		se = (int[][]) structuringElement.clone();
	}
	
	//	utility methods
	public void showFilter() {
		showFilter(se, "Structuring Element");
	}

	public void showFilter(int[][] filter, String title){
		int w = filter[0].length;
		int h = filter.length;
		ImageProcessor ip = new ByteProcessor(w,h);
		int i=0;
		for (int v=0;v<h; v++){
			for (int u=0;u<w; u++){
				if (filter[v][u]==1)
					ip.putPixel(u,v,255);
				else
					ip.putPixel(u,v,0);
				i++;
			}
		}
		ip.invertLut();
		ImagePlus win = new ImagePlus(title,ip);
		win.show();
	}
	
	public void apply(ImageProcessor ip, Operation op){
		switch(op) {
			case Dilation: this.dilate(ip,se); break;
			case Erosion: this.erode(ip,se); break;
			case Opening: this.open(ip,se); break;
			case Closing: this.close(ip,se); break;
			case Outline:  this.outline(ip); break;
			default: throw new Error("BinMorpher: unknown operation " + op);
		}
	}
	
	// morphology methods 
	
	void dilate(ImageProcessor ip, int[][] H){
		if (H == null){
			IJ.error("no structuring element");
			return;
		}

		//assume that the hot spot of se is at its center (ic,jc)
		int ic = (H[0].length - 1) / 2;
		int jc = (H.length - 1) / 2;
		int N = H.length * H[0].length;
		
		ImageProcessor tmp = ip.createProcessor(ip.getWidth(),ip.getHeight());
		
		int k = 0;
		IJ.showProgress(k,N);
		for (int j=0; j<H.length; j++){
			for (int i=0; i<H[j].length; i++){
				if (H[j][i] > 0) {	// this pixel is set
					//copy image into position (u-ch,v-cv)
					tmp.copyBits(ip,i-ic,j-jc,Blitter.MAX);
				}
				IJ.showProgress(k++,N);
			}
		}
		ip.copyBits(tmp,0,0,Blitter.COPY);
		
	}
	
	void erode(ImageProcessor ip, int[][] H){
		//dilates the background
		ip.invert();
		dilate(ip,reflect(H));
		ip.invert();
	}
	
	void open(ImageProcessor ip, int[][] H){
		erode(ip,H);
		dilate(ip,H);
	}
	
	void close(ImageProcessor ip, int[][] H){
		dilate(ip,H);
		erode(ip,H);
	}
	
	void outline(ImageProcessor ip){
		int[][] H = {{0,1,0},
			         {1,1,1},
			         {0,1,0}
			        };
		ImageProcessor foreground = ip.duplicate();
		erode(foreground,H);
		ip.copyBits(foreground,0,0,Blitter.DIFFERENCE);
	}
	
	int[][] reflect(int[][] se) {
		// mirrors the structuring element around the center (hot spot)
		// used to implement erosion by a dilation
		int N = se.length;		// number of rows
		int M = se[0].length;	// number of columns
		int[][] fse = new int[N][M];
		for (int j=0; j<N; j++) {
			for (int i=0; i<M; i++) {
				fse[j][i] = se[N-j-1][M-i-1];
			}
		}
		return fse;
	}
	
}





