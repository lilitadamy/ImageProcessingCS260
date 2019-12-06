import ij.ImagePlus;
import ij.*;
import ij.process.ImageProcessor;
import ij.plugin.filter.PlugInFilter;
import java.awt.Color;
public class Eccentricity implements PlugInFilter {
  
    public int setup(String args, ImagePlus im) {
        return DOES_RGB;
    }
    public void run(ImageProcessor ip) {
	double ecc = (centralMoment(ip,2,0) + centralMoment(ip,0,2) +
                Math.sqrt(Math.pow(centralMoment(ip,2,0) - centralMoment(ip,0,2),2) +
                        4 * Math.pow(centralMoment(ip,1,1),2))) /
                        (centralMoment(ip,2,0) + centralMoment(ip,0,2) - 
                        Math.sqrt(Math.pow(centralMoment(ip,2,0) - centralMoment(ip,0,2),2) + 
                                4 * Math.pow(centralMoment(ip,1,1),2)));
        IJ.log(Double.toString(ecc));
    }
    double moment(ImageProcessor I, int p, int q) {
        double Mpq = 0.0;
        for (int v = 0; v < I.getHeight(); v++) {
            for (int u = 0; u < I.getWidth(); u++) {
                if (I.getPixel(u, v) > 0) {
                    Mpq+= Math.pow(u, p) * Math.pow(v, q);
                }
            }
        }
        return Mpq;
    }

    double centralMoment(ImageProcessor I, int p, int q) {
        double m00 = moment(I, 0, 0); // region area
        double xCtr = moment(I, 1, 0) / m00;
        double yCtr = moment(I, 0, 1) / m00;
        double cMpq = 0.0;
        for (int v = 0; v < I.getHeight(); v++) {
            for (int u = 0; u < I.getWidth(); u++) {
                if (I.getPixel(u, v) > 0) {
                    cMpq+= Math.pow(u-xCtr, p) * Math.pow(v-yCtr, q);
                }
            }
        }
        return cMpq;
    }

    double nCentralMoment(ImageProcessor I, int p, int q) {
        double m00 = moment(I, 0, 0);
        double norm = Math.pow(m00, 0.5 * (p + q + 2));
        return centralMoment(I, p, q) / norm;
    }

}











