package br.ita.bditac.ws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.ita.bditac.model.Alerta;
import br.ita.bditac.model.AlertaDAO;
import br.ita.bditac.model.Categorias;
import br.ita.bditac.model.Crise;
import br.ita.bditac.model.Message;
import br.ita.bditac.ws.support.MessageResource;
import br.ita.bditac.ws.support.MessageResourceAssembler;

@RestController
@ExposesResourceFor(Crise.class)
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@RequestMapping("/crise")
public class CriseController {
    
    @Autowired
    private MessageResourceAssembler messageResourceAssembler;
    
    @RequestMapping(method = RequestMethod.POST, consumes = { MediaTypes.HAL_JSON_VALUE })
    public ResponseEntity<MessageResource> adicionar(@RequestBody Crise body) {
    	try {
	        Crise crise = AlertaDAO.adicionarCrise(body);
	        
	        // TODO - Simulação do gerenciamento de crises - o processo que torna o cadastramento de crise num alerta
	        Alerta alerta = new Alerta(
	        		Categorias.getCategoria(crise.getCategoria()),
	        		crise.getDescricao(),
	        		crise.getCategoria(),
	        		crise.getLatitude(),
	        		crise.getLongitude(),
	                // TODO - Sala de gerenciamento de crises determina a área de abrangência da crise
	        		10);
	        AlertaDAO.adicionarAlerta(alerta);
	        
	        Message message = new Message(1, Message.Type.INFO, HttpStatus.OK.getReasonPhrase(), "Crise registrada", "Crise registrada");
	        MessageResource resource = new MessageResource(message);
	        
	        return new ResponseEntity<MessageResource>(resource, HttpStatus.CREATED);
    	}
    	catch(Exception ex) {
	        Message message = new Message(1, Message.Type.INFO, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage(), ex.getCause().getMessage());
	        MessageResource resource = new MessageResource(message);
	        
	        return new ResponseEntity<MessageResource>(resource, HttpStatus.INTERNAL_SERVER_ERROR);	        
    	}
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<MessageResource> handleExceptions(Exception ex) {
        MessageResource resource = messageResourceAssembler.toResource(1, ex.getMessage());

        return new ResponseEntity<MessageResource>(resource, HttpStatus.BAD_REQUEST);
    }
    
}
