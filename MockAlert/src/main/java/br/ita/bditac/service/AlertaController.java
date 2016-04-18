package br.ita.bditac.service;

import java.util.List;

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
import br.ita.bditac.support.AlertaResource;
import br.ita.bditac.support.AlertaResourceAssembler;
import br.ita.bditac.support.AlertaResources;
import br.ita.bditac.support.MessageResource;
import br.ita.bditac.support.MessageResourceAssembler;

@RestController
@ExposesResourceFor(Alerta.class)
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@RequestMapping("/alerta")
public class AlertaController {

    private static final String COORD_REGEX = "(?:[-+]?(?:(?:[1-8]?\\d(?:\\.\\d+))+|90))";
    
    private static final String DIST_REGEX = "[+-]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*)(?:[eE][+-]?\\d+)?";
    
    public interface Request {
        
        String ID = "/{id}";
        
        String BY_REGIAO = "/latitude/{latitude:" + COORD_REGEX + "}" + "/longitude/{longitude:" + COORD_REGEX + "}" + "/raio/{raio:" + DIST_REGEX + "}";

    }

    @Autowired
    private AlertaResourceAssembler resourceAssembler;

    @Autowired
    private MessageResourceAssembler messageResourceAssembler;

    @Autowired
    private AlertaDAO service;

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaTypes.HAL_JSON_VALUE }, produces = { MediaTypes.HAL_JSON_VALUE })
    public ResponseEntity<AlertaResource> adicionar(@RequestBody Alerta body) {
        Alerta alerta = service.adicionarAlerta(body);
        AlertaResource resource = resourceAssembler.toResource(alerta);
        
        return new ResponseEntity<AlertaResource>(resource, HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = { MediaTypes.HAL_JSON_VALUE }, value = Request.ID)
    public ResponseEntity<AlertaResource> alerta(@PathVariable("id") int id) {
        Alerta alerta = service.obterAlerta(id);
        if(alerta == null) {
            return new ResponseEntity<AlertaResource>(HttpStatus.NOT_FOUND);
        }
        else {
            AlertaResource resource = resourceAssembler.toResource(alerta);
            
            return new ResponseEntity<AlertaResource>(resource, HttpStatus.OK);
        }
    }
    
    @RequestMapping(method = RequestMethod.HEAD, produces = { MediaTypes.HAL_JSON_VALUE }, value = Request.BY_REGIAO)
    public ResponseEntity<AlertaResource> alerta(@PathVariable("latitude") String latitude, @PathVariable("longitude") String longitude, @PathVariable("raio") String raio) {
        Double dLatitude = Double.valueOf(latitude);
        Double dLongitude = Double.valueOf(longitude);
        Double dRaio = Double.valueOf(raio);

        boolean hasAlertas = service.obterAlerta(dLatitude, dLongitude, dRaio);
        if(hasAlertas) {
            return new ResponseEntity<AlertaResource>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<AlertaResource>(HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = { MediaTypes.HAL_JSON_VALUE }, value = Request.BY_REGIAO)
    public ResponseEntity<AlertaResources> alertasPorRegiao(@PathVariable("latitude") String latitude, @PathVariable("longitude") String longitude, @PathVariable("raio") String raio) {
        Double dLatitude = Double.valueOf(latitude);
        Double dLongitude = Double.valueOf(longitude);
        Double dRaio = Double.valueOf(raio);
        
        List<Alerta> alertas = service.obterAlertasPorRegiao(dLatitude, dLongitude, dRaio);
        List<AlertaResource> resources = resourceAssembler.toResources(alertas);
        AlertaResources alertaResources = new AlertaResources(resources);
        // TODO Remover alertas após período de tempo
//        for(Alerta alerta : alertas) {
//        	service.removerAlerta(alerta.getId());
//        }
        if(alertaResources.getContent().size() == 0) {
        	return new ResponseEntity<AlertaResources>(HttpStatus.NOT_FOUND);
        }
        else {
        	return new ResponseEntity<AlertaResources>(alertaResources, HttpStatus.OK);
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
