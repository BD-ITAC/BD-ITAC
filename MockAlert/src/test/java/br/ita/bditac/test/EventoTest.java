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

import br.ita.bditac.model.Evento;
import br.ita.bditac.model.EventoResponse;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventoTest {
    
    @Test
    public void test01POSTEvento() throws URISyntaxException {
        URI eventoURI = new URI("http://localhost:8080/evento");
        
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
        Evento eventoRequest = new Evento(
                "Deslizamento na na favela do Paraiso",
                0,
                "Ze das Couves",
                "zedascouves@gmail.com",
                "(12) 99876-1234",
                endereco);
        try {
            HttpEntity<Evento> eventoRequestEntity = new HttpEntity<Evento>(eventoRequest);
            ResponseEntity<EventoResponse> eventoResponseEntity =  restTemplate.postForEntity(eventoURI, eventoRequestEntity, EventoResponse.class);
            Evento eventoResponse = eventoResponseEntity.getBody().getEvento();
            
            assertEquals("Resposta(descricao):'" + eventoResponse.getDescricao() + "' do POST diferente do que foi enviado: '" + eventoRequest.getDescricao() + "'!", eventoResponse.getDescricao(), eventoRequest.getDescricao());
            assertEquals("Resposta(categoria) do POST diferente do que foi enviado!", eventoResponse.getCategoria(), eventoRequest.getCategoria());
            assertEquals("Resposta(nome) do POST diferente do que foi enviado!", eventoResponse.getNome(), eventoRequest.getNome());
            assertEquals("Resposta(email) do POST diferente do que foi enviado!", eventoResponse.getEmail(), eventoRequest.getEmail());
            assertEquals("Resposta(telefone) do POST diferente do que foi enviado!", eventoResponse.getTelefone(), eventoRequest.getTelefone());
            int linha = 0;
            for(String linhaEndereco : eventoRequest.getEndereco()) {
                assertEquals("Resposta(endereco) do POST diferente do que foi enviado!", linhaEndereco, eventoRequest.getEndereco().get(linha++));
            }
        }
        catch(ResourceAccessException raex) {
            fail("Servico web não esta sendo executado!");
        }
        
    }
    
    @Test
    public void test02GETEvento() throws URISyntaxException {
        String eventoURL = "http://localhost:8080/evento/{id}";
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);
        
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", 1);
        
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        
        ResponseEntity<EventoResponse> eventoResponseEntity = restTemplate.getForEntity(eventoURL, EventoResponse.class, params);
        Evento eventoResponse = eventoResponseEntity.getBody().getEvento();
        
        List<String> endereco = new ArrayList<String>();
        endereco.add("rua das Casas");
        endereco.add("numero das Portas");
        Evento eventoRequest = new Evento(
                "Deslizamento na na favela do Paraiso",
                0,
                "Ze das Couves",
                "zedascouves@gmail.com",
                "(12) 99876-1234",
                endereco);

        assertEquals("Resposta(descricao):'" + eventoResponse.getDescricao() + "' do POST diferente do que foi enviado: '" + eventoRequest.getDescricao() + "'!", eventoResponse.getDescricao(), eventoRequest.getDescricao());
        assertEquals("Resposta(categoria) do POST diferente do que foi enviado!", eventoResponse.getCategoria(), eventoRequest.getCategoria());
        assertEquals("Resposta(nome) do POST diferente do que foi enviado!", eventoResponse.getNome(), eventoRequest.getNome());
        assertEquals("Resposta(email) do POST diferente do que foi enviado!", eventoResponse.getEmail(), eventoRequest.getEmail());
        assertEquals("Resposta(telefone) do POST diferente do que foi enviado!", eventoResponse.getTelefone(), eventoRequest.getTelefone());
        int linha = 0;
        for(String linhaEndereco : eventoRequest.getEndereco()) {
            assertEquals("Resposta(endereco) do POST diferente do que foi enviado!", linhaEndereco, eventoRequest.getEndereco().get(linha++));
        }
    }
    
}
