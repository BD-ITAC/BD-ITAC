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

import br.ita.bditac.model.Alerta;
import br.ita.bditac.model.AlertaDAO;
import br.ita.bditac.model.Categorias;
import br.ita.bditac.model.Evento;
import br.ita.bditac.support.EventoResource;
import br.ita.bditac.support.EventoResourceAssembler;
import br.ita.bditac.support.MessageResource;
import br.ita.bditac.support.MessageResourceAssembler;
import br.ita.bditac.support.ReverseEnum;

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
    private AlertaDAO service;
    
    @RequestMapping(method = RequestMethod.POST, consumes = { MediaTypes.HAL_JSON_VALUE })
    public ResponseEntity<EventoResource> adicionar(@RequestBody Evento body) {
        Evento evento = service.adicionarEvento(body);
        
        // TODO - Simulação do gerenciamento de crises - o processo que torna o cadastramento de evento num alerta
        Alerta alerta = new Alerta();
        ReverseEnum<Categorias> reverseCategoria = new ReverseEnum<>(Categorias.class);
        alerta.setDescricaoResumida(reverseCategoria.get(evento.getCategoria()).name());
        alerta.setDescricaoCompleta(evento.getDescricao());
        // TODO - Sala de gerenciamento de crises determina os fatores de risco
        alerta.setFatorRiscoHumano(5);
        alerta.setFatorRiscoMaterial(5);
        alerta.setCategoriaAlerta(evento.getCategoria());
        alerta.setOrigemLatitude(evento.getLatitude());
        alerta.setOrigemLongitude(evento.getLongitude());
        // TODO - Sala de gerenciamento de crises determina a área de abrangência do evento
        alerta.setOrigemRaioKms(10);
        service.adicionarAlerta(alerta);
        
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
