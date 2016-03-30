package br.ita.bditac.ws.client;


public abstract class AbstractBaseService {
    
    private String hostURL;

    public AbstractBaseService(String hostURL) {
        this.hostURL = hostURL;
    }
    
    public String getHostURL() {
        return hostURL;
    }
    
}
