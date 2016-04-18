package br.ita.bditac.service;

import org.joda.time.DateTime;
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
import br.ita.bditac.model.Crise;
import br.ita.bditac.support.CriseResource;
import br.ita.bditac.support.CriseResourceAssembler;
import br.ita.bditac.support.MessageResource;
import br.ita.bditac.support.MessageResourceAssembler;
import br.ita.bditac.support.ReverseEnum;

@RestController
@ExposesResourceFor(Crise.class)
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@RequestMapping("/crise")
public class CriseController {
    
    public interface Request {
        
        String ID = "/{id}";
        
    }
    
    @Autowired
    private CriseResourceAssembler resourceAssembler;
    
    @Autowired
    private MessageResourceAssembler messageResourceAssembler;
    
    @Autowired
    private AlertaDAO service;
    
    @RequestMapping(method = RequestMethod.POST, consumes = { MediaTypes.HAL_JSON_VALUE })
    public ResponseEntity<CriseResource> adicionar(@RequestBody Crise body) {
        Crise crise = service.adicionarcrise(body);
        
        // TODO - Simulação do gerenciamento de crises - o processo que torna o cadastramento de crise num alerta
        ReverseEnum<Categorias> reverseCategoria = new ReverseEnum<>(Categorias.class);
        Alerta alerta = new Alerta(
        		DateTime.now().toString(),
        		reverseCategoria.get(crise.getCategoria()).name(),
        		crise.getDescricao(),
        		crise.getCategoria(),
        		crise.getLatitude(),
        		crise.getLongitude(),
                // TODO - Sala de gerenciamento de crises determina a área de abrangência da crise
        		10);

        service.adicionarAlerta(alerta);
        
        CriseResource resource = resourceAssembler.toResource(crise);
        
        return new ResponseEntity<CriseResource>(resource, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, produces = { MediaTypes.HAL_JSON_VALUE }, value = Request.ID)
    public ResponseEntity<CriseResource> obter(@PathVariable("id") int id) {
        Crise crise = service.obtercrise(id);
        if(crise == null) {
            return new ResponseEntity<CriseResource>(HttpStatus.NOT_FOUND);
        }
        else {
            CriseResource resource = resourceAssembler.toResource(crise);
            
            return new ResponseEntity<CriseResource>(resource, HttpStatus.OK);
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
