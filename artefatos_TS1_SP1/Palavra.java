package testejson;

/**
 * @author Henrique
 */
public class Palavra {

    private String palavra;
    private int quant;

    public Palavra() {
    }

    public Palavra(String palavra, int quant) {
        this.palavra = palavra;
        this.quant = quant;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }
    
}
