package br.ita.bditac.ws.client;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import br.ita.bditac.ws.model.Aviso;
import br.ita.bditac.ws.model.AvisoResources;

public class AvisoClient extends AbstractBaseService {

    private static final String SERVICE_URL = "/alerts";

    public AvisoClient(String hostURL) {
        super(hostURL);
    }

    private List<Aviso> getAvisos() {

        ResponseEntity<AvisoResources> response = getRestTemplate().getForEntity(getHostURL(), AvisoResources.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().unwrap();
        }
        else {
            return null;
        }

    }

}
