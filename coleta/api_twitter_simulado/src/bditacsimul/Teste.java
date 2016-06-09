/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bditacsimul;

import java.util.Random;

/**
 *
 * @author Henrique
 */
public class Teste {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Random gerador = new Random();
        for (int x = 0; x < 10; x++) {
            float num1 = GerarRand(-5,10);
            float num2 = GerarRand(10,30);
            float num3 = GerarRand(37,100);
            int sort = gerador.nextInt(3);
            System.out.println(num1);
            System.out.println(num2);
            System.out.println(num3);
            System.out.println(sort);
        }
    }

    public static float GerarRand(float ini, float fim){
        float num=ini;
        Random gerador = new Random();
        while(num <= ini || num > fim){
            num = (float)(Math.random()*(fim-ini)+ini);
        }
        return num;
    }
}
