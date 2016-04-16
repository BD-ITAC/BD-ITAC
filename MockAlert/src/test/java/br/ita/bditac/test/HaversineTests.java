package br.ita.bditac.test;

import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.ita.bditac.support.Haversine;

/**
 * 
 * = Testes unitários da classe utilitária Haversine.
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
     * = TS02-US09
     * 
     * == Asserção:
     * 
     * Testa a distância entre três pontos obtidos através da interface web do Google Maps:
     * 
     * == Dados:
     * 
     * === Coordenadas
     *  
     * [source,java]
     * --
     * Coords itaCoords = new Coords(-23.2122219,-45.8804741);
     * Coords centerValeCoords = new Coords(-23.209816,-45.8857956);
     * Coords barCoronelCoords = new Coords(-23.1911191,-45.8951512);
     * --
     * 
     * == Execução
     * 
     * - A distância entre os pontos itaCoords e centerValeCoords é calculada e armazenada em distance1.
     * - A distância entre os pontos itaCoords e barCoronelCoords é calculada e armazenada em distance2.
     * 
     * == Resultado esperado: 
     * 
     * A medida distance1 deve ser *menor* que a medida distance2.
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
     * = TS02-US09
     * 
     * == Asserção:
     * 
     * Testa a distância entre três pontos obtidos através da interface web do Google Maps:
     * 
     * == Dados:
     * 
     * === Coordenadas
     *  
     * [source,java]
     * --
     * Coords itaCoords = new Coords(-23.2122219,-45.8804741);
     * Coords centerValeCoords = new Coords(-23.209816,-45.8857956);
     * Coords inpeCoords = new Coords(-23.211512,-45.8757535);
     * --
     * 
     * == Execução:
     * 
     * - A distância entre os pontos itaCoords e centerValeCoords é calculada e armazenada em distance1.
     * - A distância entre os pontos itaCoords e inpeCoords é calculada e armazenada em distance2.
     * 
     * == Resultado esperado: 
     * 
     * A medida distance1 deve ser *maior* que a medida distance2.
     * 
     */
    @Test
    public void test02Distance() {

        double distance1 = Haversine.distance(itaCoords.getLatitude(), itaCoords.getLongitude(), centerValeCoords.getLatitude(), centerValeCoords.getLongitude());
        double distance2 = Haversine.distance(itaCoords.getLatitude(), itaCoords.getLongitude(), inpeCoords.getLatitude(), inpeCoords.getLongitude());
        
        assertTrue("INPE deveria ser mais próximo do ITA do que o Center Vale", distance1 > distance2);

    }

}
