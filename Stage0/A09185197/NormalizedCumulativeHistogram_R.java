import ij.ImagePlus;
import ij.*;
import ij.process.ImageProcessor;
import ij.plugin.filter.PlugInFilter;
import java.awt.Color;
public class NormalizedCumulativeHistogram_R implements PlugInFilter {
    public double bottom(int x) {
        return -0.0009 * x * x + 1.1917 * x - 4.0146;
    }
    public double top(int x) {
        return -0.0011 * x * x + 1.2262 * x + 4.0264;
    }
    public int setup(String args, ImagePlus im) {
        return DOES_RGB;
    }
    public void run(ImageProcessor ip) {
        calculateCumulativeHistogram(ip);
    }
    public void calculateCumulativeHistogram(ImageProcessor ip){
        int width = ip.getWidth(), height = ip.getHeight(), pixel, r, g, b;
        double rb;
        Color color;
        double[] cum_hist_R = new double[256];
        for (int row = 0; row < height; row++){
            for (int col = 0; col < width; col++) {
                color = new Color(ip.getPixel(col, row));
                r = color.getRed();
                cum_hist_R[r] ++;               // calculating histogram for    R
            }
        }
        cum_hist_R[0] = cum_hist_R[0]/(width * height); // normalize

        for (int i = 1; i < 256; i++){
            cum_hist_R[i] = cum_hist_R[i]/(width * height);	    // normalizing histogram for       R
            cum_hist_R[i] = cum_hist_R[i] + cum_hist_R[i-1];	// calculating cumulative hist for R
            IJ.log(Double.toString(cum_hist_R[i]));
        }

    }
}













































