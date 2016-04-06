package br.ita.bditac.test;

import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.ita.bditac.support.Haversine;

/**
 * 
 * Testes unitários da classe utilitária Haversine.
 * 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HaversineTests {

    private class Coords {
        
        private double latitude;
        
        private double longitude;
        
        public Coords(double latitude, double longitude) {
            super();
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }
        
        public double getLongitude() {
            return longitude;
        }
        
    }
    
    Coords itaCoords = new Coords(-23.2122219,-45.8804741);
    Coords centerValeCoords = new Coords(-23.209816,-45.8857956);
    Coords barCoronelCoords = new Coords(-23.1911191,-45.8951512);
    Coords inpeCoords = new Coords(-23.211512,-45.8757535);
    
    /**
     * 
     * @bditac.assertion Testa a distância entre três pontos obtidas através da interface web do Google Maps:<br/>
     * 
     * @bditac.given <code>Coords itaCoords = new Coords(-23.2122219,-45.8804741);</code><br/>
     * @bditac.given <code>Coords centerValeCoords = new Coords(-23.209816,-45.8857956);</code><br/>
     * @bditac.given <code>Coords barCoronelCoords = new Coords(-23.1911191,-45.8951512);</code><br/>
     * 
     * @bditac.perform A distância entre os pontos <code>itaCoords</code> e <code>centerValeCoords</code> é calculada e armazenada em <code>distance1</code>.<br/>
     * @bditac.perform A distância entre os pontos <code>itaCoords</code> e <code>barCoronelCoords</code> é calculada e armazenada em <code>distance2</code>.<br/>
     * 
     * @bditac.expected A distância <code>distance1</code> deve ser <b>menor</b> que a distância <code>distance2</code>.<br/>
     * 
     */
    @Test
    public void test01Distance() {
        
        double distance1 = Haversine.distance(itaCoords.getLatitude(), itaCoords.getLongitude(), centerValeCoords.getLatitude(), centerValeCoords.getLongitude());
        double distance2 = Haversine.distance(itaCoords.getLatitude(), itaCoords.getLongitude(), barCoronelCoords.getLatitude(), barCoronelCoords.getLongitude());
        
        assertTrue("Bar coronel deveria ser mais longe do ITA do que o Center Vale", distance1 < distance2);
        
    }
    
    /**
     * 
     * @bditac.assertion Testa a distância entre três pontos obtidas através da interface web do Google Maps:<br/>
     * 
     * @bditac.given <code>Coords itaCoords = new Coords(-23.2122219,-45.8804741);</code><br/>
     * @bditac.given <code>Coords centerValeCoords = new Coords(-23.209816,-45.8857956);</code><br/>
     * @bditac.given <code>Coords inpeCoords = new Coords(-23.211512,-45.8757535);</code><br/>
     * 
     * @bditac.perform A distância entre os pontos <code>itaCoords</code> e <code>centerValeCoords</code> é calculada e armazenada em <code>distance1</code>.<br/>
     * @bditac.perform A distância entre os pontos <code>itaCoords</code> e <code>inpeCoords</code> é calculada e armazenada em <code>distance2</code>.<br/>
     * 
     * @bditac.expected A distância <code>distance1</code> deve ser <b>maior</b> que a distância <code>distance2</code>.<br/>
     * 
     */
    @Test
    public void test02Distance() {

        double distance1 = Haversine.distance(itaCoords.getLatitude(), itaCoords.getLongitude(), centerValeCoords.getLatitude(), centerValeCoords.getLongitude());
        double distance2 = Haversine.distance(itaCoords.getLatitude(), itaCoords.getLongitude(), inpeCoords.getLatitude(), inpeCoords.getLongitude());
        
        assertTrue("INPE deveria ser mais próximo do ITA do que o Center Vale", distance1 > distance2);

    }

}
