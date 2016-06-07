package br.ita.bditac.ws.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import br.ita.bditac.ws.model.Categorias;
import br.ita.bditac.ws.model.CategoriasResource;


public class CategoriasClient extends AbstractBaseService {

    private static final String SERVICE_URL = "/categorias";

    public CategoriasClient(String hostURL) {
        super(hostURL);
    }

    public Categorias getCategorias() {

        HttpEntity envio = new HttpEntity(getResponseHeader());

        ResponseEntity<CategoriasResource> response = getRestTemplate().exchange(getHostURL() + SERVICE_URL, HttpMethod.GET, envio, CategoriasResource.class);

        if(response.getBody() == null) {
            return null;
        }
        else {
            return response.getBody().getContent();
        }

    }

}
