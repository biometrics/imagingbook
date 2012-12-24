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

import ij.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;

public class Filter_ArbitraryMxN implements PlugInFilter {

    public int setup(String arg, ImagePlus imp) {
        if (arg.equals("about")) {
            showAbout();
            return DONE;
        }
        return DOES_8G;
    }

    public void run(ImageProcessor orig) {
        int M = orig.getWidth();
        int N = orig.getHeight();
        
        //arbitrary filter matrix of size (2K+1)x(2L+1)
        int[][] filter = {
            {0,0,1,1,1,0,0},
            {0,1,1,1,1,1,0},
            {1,1,1,1,1,1,1},
            {0,1,1,1,1,1,0},
            {0,0,1,1,1,0,0}
        };
        double s = 1.0/23;	//sum of filter coefficients is 23
        
        int K = filter[0].length/2;
        int L = filter.length/2;
        
        ImageProcessor copy = orig.duplicate();

        for (int v=L; v<=N-L-1; v++) {
            for (int u=K; u<=M-K-1; u++) {
                //compute filter result for position (u,v)
                int sum = 0;
                for (int j=-L; j<=L; j++) {
                    for (int i=-K; i<=K; i++) {
                        int p = copy.getPixel(u+i,v+j);
                        int c = filter[j+L][i+K];
                        sum = sum + c * p;
                    }
                }
                int q = (int) (s * sum);
                //clamp result
                if (q < 0)   q = 0;
                if (q > 255) q = 255;
                orig.putPixel(u,v,q);
            }
        }
    }

    void showAbout() {
        String cn = getClass().getName();
        IJ.showMessage("About "+cn+" ...",
            "MxN linear filter."
        );
    }
}
