package bditac;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Henrique
 */
public class TesteAbrev {

    public static void main(String[] args) throws IOException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("BDITACPU");

        OcorrenciaJpaController ocorjpa = new OcorrenciaJpaController(factory);

        List<Object[]> msgs = ocorjpa.findOcorrenciaMsg();

        System.out.println(msgs.size());
        
        int cont=1;
        
        PrintWriter outputStream = new PrintWriter(new FileWriter("abrev.txt"));
        
        for (Object msg : msgs) {
            System.out.println(cont+" - "+msg.toString());
            cont++;
            outputStream.println(msg.toString());
        }

        outputStream.close();
        factory.close();
    }
}
