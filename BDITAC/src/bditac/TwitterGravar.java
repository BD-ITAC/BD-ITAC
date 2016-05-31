package bditac;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import opennlp.tools.tokenize.WhitespaceTokenizer;

/**
 * @author Henrique
 */
public class TwitterGravar implements Runnable {

    int tipocrise;

    @Override
    public void run() {

        try {
            sleep(25000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TwitterGeo.class.getName()).log(Level.SEVERE, null, ex);
        }

        OcorrenciaJpaController ocorjpa = new OcorrenciaJpaController(TwitterCaptura.factory);

        int count = 0, quant = 0;

        synchronized (this) {

            while (true) {

                count = TwitterCaptura.ocors.size() - 1;

                if (count < 0) {
                    continue;
                }

                quant = count;

                System.out.println("A Gravar: " + count);

                try {

                    while (TwitterCaptura.ocors.get(count).getOcrGravado() == '0') {

                        if (quant > TwitterCaptura.ocors.size()) {
                            break;
                        }

                        Ocorrencia oc = TwitterCaptura.ocors.get(count);

//grava todos                if (oc.getOcrGeo() != '0' && oc.getOcrIdentificacao() != '0' && oc.getOcrGravado()=='0') {
                        if (oc.getOcrGeo() == 'S' && oc.getOcrIdentificacao() != '0' && oc.getOcrGravado() == '0') {
                            TwitterCaptura.ocors.get(count).setOcrGravado('S');
                            System.out.println("Gravando... - Ocorrência: " + count);
                            try {
                                ocorjpa.create(oc);
                            } catch (javax.persistence.RollbackException ex) {
                                System.out.println("Erro String...");
                            }
                        } else if (oc.getOcrGeo() == 'N' && oc.getOcrIdentificacao() == 'N' && oc.getOcrGravado() == '0') {
                            TwitterCaptura.ocors.get(count).setOcrGravado('N');
                        }

//                    if (TwitterCaptura.ocors.get(count).getOcrGravado() != '0') {
//                        TwitterCaptura.ocors.remove(count);
//                    }
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
                    System.out.println("ThreadGravar parou dormindo...");
                }
            }
        }
    }
}
