package br.ita.bditac.ws.client;

import org.springframework.http.HttpEntity;

import java.util.LinkedHashMap;
import java.util.Map;

import br.ita.bditac.ws.model.Crise;
import br.ita.bditac.ws.model.MessageResource;

public class CriseClient extends AbstractBaseService {

    private static final String SERVICE_URL = "/crisis";

    public CriseClient(String hostURL) {
        super(hostURL);
    }

    public boolean addCrise(Crise crise) {

        HttpEntity envio = new HttpEntity(crise, getRequestHeader());

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("descricao", crise.getDescricao());
        map.put("categoria", crise.getCategoria());
        map.put("nome", crise.getNome());
        map.put("email", crise.getEmail());
        map.put("telefone", crise.getTelefone());
        map.put("latitude", crise.getLatitude());
        map.put("longitude", crise.getLongitude());
        map.put("fotografia", crise.getFotografia());

        MessageResource criseResponseEntity = getRestTemplate().postForObject(getHostURL() + SERVICE_URL, new HttpEntity<Map<String, Object>>(map), MessageResource.class);

        return criseResponseEntity.getContent().getStatus().equals("Ok");

    }

}