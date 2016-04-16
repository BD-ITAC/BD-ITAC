package testejson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Henrique Louro
 */
public class TesteComunicacaoGoogle {

    public static void main(String[] args) throws MalformedURLException, IOException {
        try {
            URL url = new URL("http://g1.globo.com/jornal-nacional/noticia/2015/12/inundacoes-atingem-regiao-sul-do-brasil-uruguai-argentina-e-paraguai.html");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                String noTagRegex = "<[^>]+>";
                line = line.replaceAll(noTagRegex, "");
                System.out.println(line);
            }
            reader.close();
        } catch (MalformedURLException e) {
            // ...
        } catch (IOException e) {
            // ...
        }
    }

}
