package br.ita.bditac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.ita.bditac.model.Evento;
import br.ita.bditac.support.EventoResource;
import br.ita.bditac.support.EventoResourceAssembler;
import br.ita.bditac.support.MessageResource;
import br.ita.bditac.support.MessageResourceAssembler;

@RestController
@ExposesResourceFor(Evento.class)
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@RequestMapping("/evento")
public class EventoController {
    
    public interface Request {
        
        String ID = "/{id}";
        
    }
    
    @Autowired
    private EventoResourceAssembler resourceAssembler;
    
    @Autowired
    private MessageResourceAssembler messageResourceAssembler;
    
    @Autowired
    private AlertaService service;
    
    @RequestMapping(method = RequestMethod.POST, consumes = { MediaTypes.HAL_JSON_VALUE })
    public ResponseEntity<EventoResource> adicionar(@RequestBody Evento body) {
        Evento evento = service.adicionarEvento(body);
        EventoResource resource = resourceAssembler.toResource(evento);
        
        return new ResponseEntity<EventoResource>(resource, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, produces = { MediaTypes.HAL_JSON_VALUE }, value = Request.ID)
    public ResponseEntity<EventoResource> obter(@PathVariable("id") int id) {
        Evento evento = service.obterEvento(id);
        if(evento == null) {
            return new ResponseEntity<EventoResource>(HttpStatus.NOT_FOUND);
        }
        else {
            EventoResource resource = resourceAssembler.toResource(evento);
            
            return new ResponseEntity<EventoResource>(resource, HttpStatus.OK);
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
