package bditacsimul;

import java.util.Random;

/**
 * @author Henrique
 */
public class Teste2 {

    public static void main(String[] args) {
        Random gerador = new Random();

        int horasort=0;
        int c = 0;
        while (horasort <= 0 && c < 10) {
            horasort = gerador.nextInt(23);
            c++;
        }
        if (horasort <= Integer.parseInt(Util.lerHora().substring(0, 2))) {
            horasort = Integer.parseInt(Util.lerHora().substring(0, 2)) + 1;
        }
        System.out.println("Início: " + horasort + " h");
    }
}
