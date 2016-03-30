package br.ita.bditac.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ita.bditac.model.Alerta;
import br.ita.bditac.model.AlertaResponse;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlertaTest {

    private static final double DELTA = 1e-6;
    
    @Test
    public void test01POSTAlerta() throws URISyntaxException {
        URI alertaURI = new URI("http://localhost:8080/alerta");
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);
        
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        
        List<String> endereco = new ArrayList<String>();
        endereco.add("rua das Casas");
        endereco.add("numero das Portas");
        Alerta alertaRequest = new Alerta(
                "Alerta de deslizamento",
                "Perigo de deslizamento na altura do Km 20 da rodovia Tamoios, pista Sao Jose dos Campos/Litoral",
                5,
                5,
                0,
                0.50,
                0.50,
                1.0,
                0.50,
                0.50,
                1.0,
                endereco);
        try {
            HttpEntity<Alerta> alertaRequestEntity = new HttpEntity<Alerta>(alertaRequest);
            ResponseEntity<AlertaResponse> alertaResponseEntity = restTemplate.postForEntity(alertaURI, alertaRequestEntity, AlertaResponse.class);
            Alerta alertaResponse = alertaResponseEntity.getBody().getAlerta();
            
            assertEquals("Resposta(descricaoResumida)'" + alertaResponse.getDescricaoResumida() + "' do POST diferente do que foi enviado: '" + alertaRequest.getDescricaoResumida() + "'!", alertaResponse.getDescricaoResumida(), alertaRequest.getDescricaoResumida());
            assertEquals("Resposta(descricaoCompleta)'" + alertaResponse.getDescricaoCompleta() + "' do POST diferente do que foi enviado'" + alertaRequest.getDescricaoCompleta() + "'!", alertaResponse.getDescricaoCompleta(), alertaRequest.getDescricaoCompleta());
            assertEquals("Resposta(fatorRiscoHumano)'" + alertaResponse.getFatorRiscoHumano() + "' do POST diferente do que foi enviado'" + alertaRequest.getFatorRiscoHumano() + "'!", alertaResponse.getFatorRiscoHumano(), alertaRequest.getFatorRiscoHumano());
            assertEquals("Resposta(fatorRiscoMaterial)'" + alertaResponse.getFatorRiscoMaterial() + "' do POST diferente do que foi enviado'" + alertaRequest.getFatorRiscoMaterial() + "'!", alertaResponse.getFatorRiscoMaterial(), alertaRequest.getFatorRiscoMaterial());
            assertEquals("Resposta(categoriaAlerta)'" + alertaResponse.getCategoriaAlerta() + "' do POST diferente do que foi enviado'" + alertaRequest.getCategoriaAlerta() + "'!", alertaResponse.getCategoriaAlerta(), alertaRequest.getCategoriaAlerta());
            assertEquals("Resposta(origemLatitude)'" + alertaResponse.getOrigemLatitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getOrigemLatitude() + "'!", alertaResponse.getOrigemLatitude(), alertaRequest.getOrigemLatitude(), DELTA);
            assertEquals("Resposta(origemLongitude)'" + alertaResponse.getOrigemLongitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getOrigemLongitude() + "'!", alertaResponse.getOrigemLongitude(), alertaRequest.getOrigemLongitude(), DELTA);
            assertEquals("Resposta(origemRaioKms)'" + alertaResponse.getOrigemRaioKms() + "' do POST diferente do que foi enviado'" + alertaRequest.getOrigemRaioKms() + "'!", alertaResponse.getOrigemRaioKms(), alertaRequest.getOrigemRaioKms(), DELTA);
            assertEquals("Resposta(destinoLatitude)'" + alertaResponse.getDestinoLatitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getDestinoLatitude() + "'!", alertaResponse.getDestinoLatitude(), alertaRequest.getDestinoLatitude(), DELTA);
            assertEquals("Resposta(destinoLongitude)'" + alertaResponse.getDestinoLongitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getDestinoLongitude() + "'!", alertaResponse.getDestinoLongitude(), alertaRequest.getDestinoLongitude(), DELTA);
            assertEquals("Resposta(destinoRaio)'" + alertaResponse.getDestinoRaioKms() + "' do POST diferente do que foi enviado'" + alertaRequest.getDestinoRaioKms() + "'!", alertaResponse.getDestinoRaioKms(), alertaRequest.getDestinoRaioKms(), DELTA);
            int linha = 0;
            for(String linhaEndereco : alertaRequest.getEndereco()) {
                assertEquals("Resposta(endereco)'" + " do POST diferente do que foi enviado'" + "'!", linhaEndereco, alertaRequest.getEndereco().get(linha++));
            }
        }
        catch(ResourceAccessException raex) {
            fail("Servico web não esta sendo executado!");
        }
    }
    
    @Test
    public void test02GETEvento() throws URISyntaxException {
        String alertaURL = "http://localhost:8080/alerta/{id}";
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);
        
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", 1);
        
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        
        ResponseEntity<AlertaResponse> alertaResponseEntity = restTemplate.getForEntity(alertaURL, AlertaResponse.class, params);
        Alerta alertaResponse = alertaResponseEntity.getBody().getAlerta();
        
        List<String> endereco = new ArrayList<String>();
        endereco.add("rua das Casas");
        endereco.add("numero das Portas");
        Alerta alertaRequest = new Alerta(
                "Alerta de deslizamento",
                "Perigo de deslizamento na altura do Km 20 da rodovia Tamoios, pista Sao Jose dos Campos/Litoral",
                5,
                5,
                0,
                0.50,
                0.50,
                1.0,
                0.50,
                0.50,
                1.0,
                endereco);
        
        assertEquals("Resposta(descricaoResumida)'" + alertaResponse.getDescricaoResumida() + "' do POST diferente do que foi enviado: '" + alertaRequest.getDescricaoResumida() + "'!", alertaResponse.getDescricaoResumida(), alertaRequest.getDescricaoResumida());
        assertEquals("Resposta(descricaoCompleta)'" + alertaResponse.getDescricaoCompleta() + "' do POST diferente do que foi enviado'" + alertaRequest.getDescricaoCompleta() + "'!", alertaResponse.getDescricaoCompleta(), alertaRequest.getDescricaoCompleta());
        assertEquals("Resposta(fatorRiscoHumano)'" + alertaResponse.getFatorRiscoHumano() + "' do POST diferente do que foi enviado'" + alertaRequest.getFatorRiscoHumano() + "'!", alertaResponse.getFatorRiscoHumano(), alertaRequest.getFatorRiscoHumano());
        assertEquals("Resposta(fatorRiscoMaterial)'" + alertaResponse.getFatorRiscoMaterial() + "' do POST diferente do que foi enviado'" + alertaRequest.getFatorRiscoMaterial() + "'!", alertaResponse.getFatorRiscoMaterial(), alertaRequest.getFatorRiscoMaterial());
        assertEquals("Resposta(categoriaAlerta)'" + alertaResponse.getCategoriaAlerta() + "' do POST diferente do que foi enviado'" + alertaRequest.getCategoriaAlerta() + "'!", alertaResponse.getCategoriaAlerta(), alertaRequest.getCategoriaAlerta());
        assertEquals("Resposta(origemLatitude)'" + alertaResponse.getOrigemLatitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getOrigemLatitude() + "'!", alertaResponse.getOrigemLatitude(), alertaRequest.getOrigemLatitude(), DELTA);
        assertEquals("Resposta(origemLongitude)'" + alertaResponse.getOrigemLongitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getOrigemLongitude() + "'!", alertaResponse.getOrigemLongitude(), alertaRequest.getOrigemLongitude(), DELTA);
        assertEquals("Resposta(origemRaioKms)'" + alertaResponse.getOrigemRaioKms() + "' do POST diferente do que foi enviado'" + alertaRequest.getOrigemRaioKms() + "'!", alertaResponse.getOrigemRaioKms(), alertaRequest.getOrigemRaioKms(), DELTA);
        assertEquals("Resposta(destinoLatitude)'" + alertaResponse.getDestinoLatitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getDestinoLatitude() + "'!", alertaResponse.getDestinoLatitude(), alertaRequest.getDestinoLatitude(), DELTA);
        assertEquals("Resposta(destinoLongitude)'" + alertaResponse.getDestinoLongitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getDestinoLongitude() + "'!", alertaResponse.getDestinoLongitude(), alertaRequest.getDestinoLongitude(), DELTA);
        assertEquals("Resposta(destinoRaio)'" + alertaResponse.getDestinoRaioKms() + "' do POST diferente do que foi enviado'" + alertaRequest.getDestinoRaioKms() + "'!", alertaResponse.getDestinoRaioKms(), alertaRequest.getDestinoRaioKms(), DELTA);
        int linha = 0;
        for(String linhaEndereco : alertaRequest.getEndereco()) {
            assertEquals("Resposta(endereco)'" + " do POST diferente do que foi enviado'" + "'!", linhaEndereco, alertaRequest.getEndereco().get(linha++));
        }
    }
    
}
