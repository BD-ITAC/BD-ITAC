package br.ita.bditac.ws.service;

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
import br.ita.bditac.model.Message;
import br.ita.bditac.ws.support.AlertaResource;
import br.ita.bditac.ws.support.AlertaResourceAssembler;
import br.ita.bditac.ws.support.AlertaResources;
import br.ita.bditac.ws.support.MessageResource;
import br.ita.bditac.ws.support.MessageResourceAssembler;

@RestController
@ExposesResourceFor(Alerta.class)
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@RequestMapping("/alerta")
public class AlertaController {

    private static final String COORD_REGEX = "(?:[-+]?(?:(?:[1-8]?\\d(?:\\.\\d+))+|90))";
    
    private static final String DIST_REGEX = "[+-]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*)(?:[eE][+-]?\\d+)?";
    
    public interface Request {
        
        String BY_REGIAO = "/timestamp/{timestamp}/latitude/{latitude:" + COORD_REGEX + "}" + "/longitude/{longitude:" + COORD_REGEX + "}" + "/raio/{raio:" + DIST_REGEX + "}";

    }

    @Autowired
    private AlertaResourceAssembler resourceAssembler;

    @Autowired
    private MessageResourceAssembler messageResourceAssembler;

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaTypes.HAL_JSON_VALUE }, produces = { MediaTypes.HAL_JSON_VALUE })
    public ResponseEntity<MessageResource> adicionar(@RequestBody Alerta body) {
    	try {
    		AlertaDAO.adicionarAlerta(body);
	        
	        Message message = new Message(1, Message.Type.INFO, HttpStatus.OK.getReasonPhrase(), "Alerta registrado", "BD-ITAC");
	        MessageResource resource = new MessageResource(message);
	        
	        return new ResponseEntity<MessageResource>(resource, HttpStatus.CREATED);
    	}
    	catch(Exception ex) {
	        Message message = new Message(1, Message.Type.INFO, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage(), ex.getCause().getMessage());
	        MessageResource resource = new MessageResource(message);
	        
	        return new ResponseEntity<MessageResource>(resource, HttpStatus.INTERNAL_SERVER_ERROR);	        
    	}
    }
    
    @RequestMapping(method = RequestMethod.HEAD, produces = { MediaTypes.HAL_JSON_VALUE }, value = Request.BY_REGIAO)
    public ResponseEntity<MessageResource> alerta(@PathVariable("timestamp") long timestamp, @PathVariable("latitude") String latitude, @PathVariable("longitude") String longitude, @PathVariable("raio") String raio) {
    	try {
	        Double dLatitude = Double.valueOf(latitude);
	        Double dLongitude = Double.valueOf(longitude);
	        Double dRaio = Double.valueOf(raio);
	
	        boolean hasAlertas = AlertaDAO.obterAlerta(timestamp, dLatitude, dLongitude, dRaio);
	        if(hasAlertas) {
	            return new ResponseEntity<MessageResource>(HttpStatus.OK);
	        }
	        else {
	            return new ResponseEntity<MessageResource>(HttpStatus.NOT_FOUND);
	        }
    	}
    	catch(Exception ex) {
	        Message message = new Message(1, Message.Type.INFO, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage(), ex.getCause().getMessage());
	        MessageResource resource = new MessageResource(message);
	        
	        return new ResponseEntity<MessageResource>(resource, HttpStatus.INTERNAL_SERVER_ERROR);	        
    	}
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = { MediaTypes.HAL_JSON_VALUE }, value = Request.BY_REGIAO)
    public ResponseEntity<AlertaResources> alertasPorRegiao(@PathVariable("timestamp") long timestamp, @PathVariable("latitude") String latitude, @PathVariable("longitude") String longitude, @PathVariable("raio") String raio) {
    	try {
	        Double dLatitude = Double.valueOf(latitude);
	        Double dLongitude = Double.valueOf(longitude);
	        Double dRaio = Double.valueOf(raio);
	        
	        List<Alerta> alertas = AlertaDAO.obterAlertasPorRegiao(timestamp, dLatitude, dLongitude, dRaio);
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
    	catch(Exception ex) {
	        return new ResponseEntity<AlertaResources>(HttpStatus.INTERNAL_SERVER_ERROR);	        
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
