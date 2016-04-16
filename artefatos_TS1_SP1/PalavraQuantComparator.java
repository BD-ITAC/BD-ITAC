package testejson;

import java.util.Comparator;

/**
 * @author Henrique
 */

public class PalavraQuantComparator  implements Comparator<Palavra>{

    @Override
    public int compare(Palavra pal1, Palavra pal2) {
        if (pal1.getQuant() < pal2.getQuant()) {
            return 1;
        } else if (pal1.getQuant() == pal2.getQuant()) {
            return 0;
        }
        return -1;
    }
}
