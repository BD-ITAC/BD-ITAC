package br.ita.bditac.ws.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.ita.bditac.model.Mensagem;
import br.ita.bditac.model.SensorDAO;
import br.ita.bditac.model.Topico;
import br.ita.bditac.ws.support.MessageResource;
import br.ita.bditac.ws.support.MessageResourceAssembler;


@RestController
@ExposesResourceFor(Topico.class)
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@RequestMapping("/topico")
public class TopicoController {
	
	@Autowired
	private MessageResourceAssembler messageResourceAssembler;

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = { MediaTypes.HAL_JSON_VALUE })
	public ResponseEntity<MessageResource> adicionar(@RequestBody Topico body) {
		try {
			SensorDAO.adicionarTopico(body.getDescricao());
			
			return new ResponseEntity<MessageResource>(
					new MessageResource(
							new Mensagem(1, Mensagem.Type.INFO, HttpStatus.OK.getReasonPhrase(), "Tópico adicionado", "Tópico adicionado")), HttpStatus.CREATED);
		}
		catch(Exception ex) {
	        Mensagem mensagem = new Mensagem(1, Mensagem.Type.INFO, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage(), ex.getCause().getMessage());
	        MessageResource resource = new MessageResource(mensagem);
	        
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
