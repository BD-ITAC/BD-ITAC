package bditac;

import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * @author Henrique
 */
public class TestePolygon {

    public static void main(String[] args) {
        Polygon rco = new Polygon();
        rco.addPoint(-47,-24);
        rco.addPoint(-47,-22);
        rco.addPoint(-45,-22);
        rco.addPoint(-45, -24);
        System.out.println(rco.getBounds().toString());

        System.out.println(rco.contains(-46.108612,-23.306164));
        System.out.println(rco.contains(-46.108612,-22.816672));
        System.out.println(rco.contains(-45.726272,-22.816672));
        System.out.println(rco.contains(-45.726272,-23.306164));
        System.out.println(rco.contains(-45.8630127,-23.1895063));
    }

    public static boolean intersecRet(double ax, double ay,
            double bx, double by, double cx, double cy,
            double dx, double dy) {
        double px = Math.min(ax, bx);
        double py = Math.min(ay, by);
        double qx = Math.max(ax, bx);
        double qy = Math.max(ay, by);
        double p1x = Math.min(cx, dx);
        double p1y = Math.min(cy, dy);
        double q1x = Math.max(cx, dx);
        double q1y = Math.max(cy, dy);
        return ((qx >= p1x) && (q1x >= px) && 
                (qy >= p1y) && (q1y >= py));
    }
}
