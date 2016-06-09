package bditac;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Henrique
 */
public class TesteAbrevGerar {

    public static void main(String[] args) throws IOException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("BDITACPU");

        PalavraJpaController paljpa = new PalavraJpaController(factory);
        
        AbreviacaoJpaController abrevjpa = new AbreviacaoJpaController(factory);
        
        PrintWriter outstr = new PrintWriter(new FileWriter("abrevgerar.txt"));

        File file = new File("abrevalunos.txt");
        BufferedReader arq = null;
        String linha;
        int cont = 0;
        if (file.canRead() || file.length() > 0) {
            try {
                arq = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException ex) {
                System.out.println("Erro ao ler arquivo...");
                System.exit(0);
            }
            try {
                String palavra,abreviacao;
                String temp[];
                Palavra pal;
                Abreviacao abrev;
                int id;
                while ((linha = arq.readLine()) != null) {
                    cont++;

                    temp = linha.split(";");
                    abreviacao = temp[0];
                    abrev = abrevjpa.findAbreviacaoAbrev(abreviacao);
                    if(abrev != null){
                        continue;
                    }
                    palavra = temp[1];
                    pal = paljpa.findPalavraPalavra(palavra);
                    if (pal == null) {
                        id = 2;
                    } else {
                        id = pal.getPalId();
                    }

                    outstr.println(temp[0] + ";" + temp[1] + ";" + id);

                    abrev = new Abreviacao();
                    abrev.setAbvAbreviacao(abreviacao);
                    abrev.setAbvExplicacao(palavra);
                    abrev.setPalId(id);
                    
                    abrevjpa.create(abrev);
                    
                    System.out.println(cont);
                }
                arq.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        factory.close();
        outstr.close();
        System.exit(0);
    }
}
