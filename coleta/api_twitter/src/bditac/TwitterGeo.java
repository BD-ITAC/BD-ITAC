package bditac;

import java.awt.Polygon;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Henrique
 */
public class TwitterGeo implements Runnable {

    String coords[] = null;
    String cidnome = "";
    char geotipo;

    TwitterGeo(String[] coords, String cidNome, Character criGeotipo) {
        this.coords = coords;
        cidnome = cidNome;
        geotipo = criGeotipo;
    }

    @Override
    public void run() {
        try {
            sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TwitterGeo.class.getName()).log(Level.SEVERE, null, ex);
        }
        int count = 0, quant = 0;
        String geo;
        String temp[];

        Polygon rco = new Polygon();
        rco.addPoint(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
        rco.addPoint(Integer.parseInt(coords[2]), Integer.parseInt(coords[3]));
        rco.addPoint(Integer.parseInt(coords[4]), Integer.parseInt(coords[5]));
        rco.addPoint(Integer.parseInt(coords[6]), Integer.parseInt(coords[7]));

        synchronized (this) {

            while (true) {

                count = TwitterCaptura.ocors.size() - 1;

                if (count < 0) {
                    continue;
                }

                quant = count;

                try {

                    while (TwitterCaptura.ocors.get(count).getOcrGeo() == '0') {

                        if (quant > TwitterCaptura.ocors.size()) {
                            break;
                        }

                        Ocorrencia oc = TwitterCaptura.ocors.get(count);
                        geo = oc.getOcrCoordenadas().replaceAll("\\[", "").replaceAll("\\]", "");
                        if (geo.length() == 0) {
                            TwitterCaptura.ocors.get(count).setOcrGeo('N');
                            count--;
                            if (count < 0) {
                                break;
                            }
                            continue;
                        }
                        geo = geo.substring(0, geo.length() - 1);
                        temp = geo.split(",");
//                System.out.println(count + " - " + oc.getOcrIdApi() + " - " + geo);
                        if ((geotipo == '1') && (rco.contains(Double.parseDouble(temp[0]), Double.parseDouble(temp[1])))
                                && (rco.contains(Double.parseDouble(temp[2]), Double.parseDouble(temp[3])))
                                && (rco.contains(Double.parseDouble(temp[4]), Double.parseDouble(temp[5])))
                                && (rco.contains(Double.parseDouble(temp[6]), Double.parseDouble(temp[7])))) {
                            TwitterCaptura.ocors.get(count).setOcrGeo('S');
                        } else if ((geotipo == '2') && (oc.getOcrLocal().contains(cidnome))) {
                            TwitterCaptura.ocors.get(count).setOcrGeo('S');
                        } else if ((geotipo == '3') && (rco.contains(Double.parseDouble(temp[0]), Double.parseDouble(temp[1])))
                                && (rco.contains(Double.parseDouble(temp[2]), Double.parseDouble(temp[3])))
                                && (rco.contains(Double.parseDouble(temp[4]), Double.parseDouble(temp[5])))
                                && (rco.contains(Double.parseDouble(temp[6]), Double.parseDouble(temp[7])))
                                && (oc.getOcrLocal().contains(cidnome))) {
                            TwitterCaptura.ocors.get(count).setOcrGeo('S');
                        } else {
                            TwitterCaptura.ocors.get(count).setOcrGeo('N');
                        }

//                System.out.println("Situação: "+oc.getOcrGeo());
                        count--;

                        if (count < 0) {
                            break;
                        }

                        if (quant > TwitterCaptura.ocors.size()) {
                            break;
                        }

                    }
                } catch (IndexOutOfBoundsException | java.lang.NullPointerException ex) {
                    System.out.println(ex);
                }
                try {
                    sleep(5000);
                } catch (IllegalThreadStateException | InterruptedException ex) {
                    System.out.println("ThreadGeo parou dormindo...");
                }
            }
        }
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
        return ((qx >= p1x) && (q1x >= px)
                && (qy >= p1y) && (q1y >= py));
    }
}
