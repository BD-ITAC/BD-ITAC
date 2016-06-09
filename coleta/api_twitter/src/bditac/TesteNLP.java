/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bditac;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

/**
 *
 * @author Henrique
 */
public class TesteNLP {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    
    public static void main(String[] args) throws IOException {
        POSModel model = new POSModelLoader().load(new File("pt-pos-maxent.bin"));
        PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
        POSTaggerME tagger = new POSTaggerME(model);

        String input = "Olá. Como você está? Aqui é o Henrique.";
        ObjectStream<String> lineStream = new PlainTextByLineStream(new StringReader(input));

        perfMon.start();
        String line;
        String cars = "[!@#$%&*()\\-\\_\\+=`´^~<>,.;:?/]";
        
        while ((line = lineStream.read()) != null) {

            System.out.println(line);
            System.out.println(line.replaceAll(cars,""));
            
            String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE
                    .tokenize(line);
            String[] tags = tagger.tag(whitespaceTokenizerLine);

            for(String w : whitespaceTokenizerLine){
                System.out.println(w);
            }
            for(String s : tags){
                System.out.println(s);
            }
            POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
            System.out.println(sample.toString());


            perfMon.incrementCounter();
        }
        perfMon.stopAndPrintFinalResult();
    }
}
