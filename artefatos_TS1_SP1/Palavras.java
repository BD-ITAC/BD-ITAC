package testejson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 * @author Henrique Louro
 */
public class Palavras {

    public static void main(String[] args) {
        List<Palavra> palavras = new ArrayList();
        File file = new File("inundacao.txt");
        BufferedReader arq = null;
        String linha;
        String palavra;
        boolean achei;
        int cont = 0;
        if (file.canRead() || file.length() > 0) {
            try {
                arq = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException ex) {
            }
            try {
                while ((linha = arq.readLine()) != null) {
                    Pattern p = Pattern.compile("[a-zA-Zà-úÀ-Ú]+");
                    Matcher m = p.matcher(linha);
                    while (m.find()) {
                        palavra = m.group();
                        if (palavra.length()<3){
                            continue;
                        }
                        cont++;
                        palavra = palavra.toLowerCase();
                        achei = false;
                        for(Palavra pal : palavras){
                            if(pal.getPalavra().equals(palavra)){
                                pal.setQuant(pal.getQuant()+1);
                                achei=true;
                                break;
                            }
                        }
                        if(!achei){
                            palavras.add(new Palavra(palavra,1));
                        }
                    }
                }
                arq.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }//fecha if
        else {
            JOptionPane.showMessageDialog(null, "Arquivo Inválido! " + file.canRead() + "//" + file.length(), "Palavras", JOptionPane.ERROR_MESSAGE);
        }
        
        palavras.sort(new PalavraQuantComparator());

        System.out.println("Palavras Avaliadas: "+cont);
        System.out.println("Palavras Selecionadas: "+palavras.size());
        for(Palavra pal : palavras){
            System.out.println(pal.getPalavra()+" - "+pal.getQuant());
        }
    }
}
