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
public class TwitterMens implements Runnable {

    int tipocrise;

    public TwitterMens(int tipocrise) {
        this.tipocrise = tipocrise;
    }

    @Override
    public void run() {

        try {
            sleep(15000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TwitterGeo.class.getName()).log(Level.SEVERE, null, ex);
        }

        AbreviacaoJpaController jpaabrev = new AbreviacaoJpaController(TwitterCaptura.factory);

        List<Abreviacao> abrevs = jpaabrev.findAbreviacaoEntities();

        CriseIdentificacaoJpaController jpacriident = new CriseIdentificacaoJpaController(TwitterCaptura.factory);

        List<CriseIdentificacao> criidents = jpacriident.findCriseIdentificacaoTipo(tipocrise);

        List<String> palavras = new ArrayList();

        PalavraJpaController jpapal = new PalavraJpaController(TwitterCaptura.factory);

        Palavra pal;

        for (CriseIdentificacao ci : criidents) {
            pal = jpapal.findPalavra(ci.getPalId());
            palavras.add(pal.getPalPalavra());
        }

        int count = 0, quant = 0;

        String frase;
        String cars = "[!@#$%&*()\\-\\_\\+=`´^~<>,.;:?/]";
        int achou;

        synchronized (this) {
            while (true) {

                count = TwitterCaptura.ocors.size() - 1;

                if (count < 0) {
                    continue;
                }

                quant = count;

                try {

                    while (TwitterCaptura.ocors.get(count).getOcrIdentificacao() == '0') {

                        if (quant > TwitterCaptura.ocors.size()) {
                            break;
                        }

                        Ocorrencia oc = TwitterCaptura.ocors.get(count);

                        if (oc.getOcrGeo() == '0') {
                            count--;
                            if (count < 0) {
                                break;
                            }
                            continue;
                        } else if (oc.getOcrGeo() == 'N') {
                            TwitterCaptura.ocors.get(count).setOcrIdentificacao('N');
                            TwitterCaptura.ocors.get(count).setOcrIdenper(0.0f);
                            count--;
                            if (count < 0) {
                                break;
                            }
                            continue;
                        }

                        frase = oc.getOcrTexto();

//                System.out.println(count + " - " + oc.getOcrIdApi() + " - " + frase);
                        frase = CorrigePontuacao(frase);

//                System.out.println(frase);
                        frase = Limpar(frase);

//                System.out.println(frase);
                        String palavra[] = WhitespaceTokenizer.INSTANCE.tokenize(frase);

                        frase = "";
                        for (String p : palavra) {
                            if (cars.contains(p)) {
                                frase = frase + p + " ";
                                continue;
                            }
                            for (Abreviacao ab : abrevs) {
                                if (p.replaceAll(cars, "").equals(ab.getAbvAbreviacao())) {
                                    p = p + "<" + ab.getAbvExplicacao() + ">";
                                    break;
                                }
                            }
                            frase = frase + p + " ";
                        }

                        frase = frase.trim();

//                System.out.println(frase);
                        achou = 0;
                        palavra = WhitespaceTokenizer.INSTANCE.tokenize(frase);

                        for (String p : palavra) {
                            if (p.contains("<")) {
                                p = LimparAbrev(p);
                            }
                            for (String ps : palavras) {
                                if (p.replaceAll(cars, "").equals(ps)) {
                                    achou++;
                                    break;
                                }
                            }
                        }

                        oc.setOcrTexto(frase);
                        TwitterCaptura.ocors.get(count).setOcrTexto(frase);

                        if (achou > 0) {
                            TwitterCaptura.ocors.get(count).setOcrIdentificacao('S');
                            TwitterCaptura.ocors.get(count).setOcrIdenper((float) achou * 100 / palavras.size());
                        } else {
                            TwitterCaptura.ocors.get(count).setOcrIdentificacao('N');
                            TwitterCaptura.ocors.get(count).setOcrIdenper(0.0f);
                        }
                        //              System.out.println("Identificação: " + oc.getOcrIdentificacao() + " - " + oc.getOcrIdenper());
                        //              System.out.println("Posição: " + count + " - " + oc.getOcrGeo());
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
                    System.out.println("ThreadMens parou dormindo...");
                }
            }
        }
    }

    private String Limpar(String texto) {
        Pattern pat = Pattern.compile("[a-zA-Zà-úÀ-Ú0-9Çç!:,.?]+");
        Matcher m;
        String palavra;
        String wt[];
        boolean achou = false;
        m = pat.matcher(texto);
        String frase = "";
        while (m.find()) {
            palavra = m.group();
            frase += palavra + " ";
        }
        frase = frase.trim();
        return frase;
    }

    private String CorrigePontuacao(String frase) {
        String car;
        String cars = "[!,.:;?]";
        for (int x = 0; x < frase.length() - 1; x++) {
            car = frase.substring(x, x + 1);
            if (cars.contains(car)) {
                if (!(frase.substring(x + 1, x + 2).equals(" ")) && !cars.contains(frase.substring(x + 1, x + 2))) {
                    frase = frase.substring(0, x + 1) + " " + frase.substring(x + 1);
                }
            }
        }
        return frase;
    }

    private String LimparAbrev(String texto) {
        return texto = texto.substring(texto.indexOf("<") + 1, texto.length() - 1);
    }
}
