package br.ita.bditac.ws.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import br.ita.bditac.ws.model.Categoria;
import br.ita.bditac.ws.model.CategoriaResources;


public class CategoriasClient extends AbstractBaseService {

    private static final String SERVICE_URL = "/categorias";

    public CategoriasClient(String hostURL) {
        super(hostURL);
    }

    public List<Categoria> getCategorias() {

        HttpEntity envio = new HttpEntity(getResponseHeader());

        ResponseEntity<CategoriaResources> response = getRestTemplate().exchange(getHostURL() + SERVICE_URL, HttpMethod.GET, envio, CategoriaResources.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().unwrap();
        }
        else {
            return null;
        }

    }

}
