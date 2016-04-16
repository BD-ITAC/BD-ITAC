package br.ita.bditac.support;


/**
 * 
 * @author BD-ITAC
 * @see <a href="https://rosettacode.org/wiki/Haversine_formula#Java">Rosetta Code</a>
 *
 */
public class Haversine {

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        
        /**
         * User Kaimbridge clarified on the Talk page:

             -- 6371.0 km is the authalic radius based on/extracted from surface area;
             -- 6372.8 km is an approximation of the radius of the average circumference
                (i.e., the average great-elliptic or great-circle radius), where the
                 boundaries are the meridian (6367.45 km) and the equator (6378.14 km).
            
            Using either of these values results, of course, in differing distances:
            
             6371.0 km -> 2886.44444283798329974715782394574671655 km;
             6372.8 km -> 2887.25995060711033944886005029688505340 km;
             (results extended for accuracy check:  Given that the radii are only
              approximations anyways, .01' ≈ 1.0621333 km and .001" ≈ .00177 km,
              practical precision required is certainly no greater than about
              .0000001——i.e., .1 mm!)
            
            As distances are segments of great circles/circumferences, it is
            recommended that the latter value (r = 6372.8 km) be used (which
            most of the given solutions have already adopted, anyways). 

         */
        final double R = 6372.8D;
        
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
 
        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        
        return R * c;
        
    }
    
}
