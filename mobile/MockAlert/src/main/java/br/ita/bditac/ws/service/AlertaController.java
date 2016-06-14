package br.ita.bditac.ws.service;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.ita.bditac.model.Alerta;
import br.ita.bditac.model.AlertaDAO;
import br.ita.bditac.ws.support.AlertaResource;
import br.ita.bditac.ws.support.AlertaResourceAssembler;
import br.ita.bditac.ws.support.AlertaResources;
import br.ita.bditac.ws.support.MessageResource;
import br.ita.bditac.ws.support.MessageResourceAssembler;

@RestController
@ExposesResourceFor(Alerta.class)
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@RequestMapping("/rest")
public class AlertaController {
    
    public interface Request {
        
    	String BY_ID = "/avisos/{id}"; //:"+ ID_REGEX + "}";
    	
        //String BY_REGIAO = "/timestamp/{timestamp}/latitude/{latitude:" + COORD_REGEX + "}" + "/longitude/{longitude:" + COORD_REGEX + "}" + "/raio/{raio:" + DIST_REGEX + "}";
    	String BY_REGIAO = "/avisos/nearbyWarnings";

    }

    @Autowired
    private AlertaResourceAssembler resourceAssembler;

    @Autowired
    private MessageResourceAssembler messageResourceAssembler;
    
    @RequestMapping(method = RequestMethod.GET, produces = { MediaTypes.HAL_JSON_VALUE }, value = Request.BY_REGIAO)
    public ResponseEntity<AlertaResources> alertasPorRegiao(@QueryParam("timestamp") String timestamp, @QueryParam("latitude") double latitude, @QueryParam("longitude") double longitude, @QueryParam("raio") double raio) {
    	try {
	        Double dLatitude = Double.valueOf(latitude);
	        Double dLongitude = Double.valueOf(longitude);
	        Double dRaio = Double.valueOf(raio);
	        
	        List<Alerta> alertas = AlertaDAO.obterAlertasPorRegiao(0, dLatitude, dLongitude, dRaio);
	        List<AlertaResource> resources = resourceAssembler.toResources(alertas);
	        AlertaResources alertaResources = new AlertaResources(resources);
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
    
    @RequestMapping(method = RequestMethod.GET, produces = { MediaTypes.HAL_JSON_VALUE }, value = Request.BY_ID)
    public ResponseEntity<AlertaResource> alerta(@PathVariable("id") int id) {
    	try {
	        Alerta alerta = AlertaDAO.obterAlerta(id);
	        AlertaResource resource = resourceAssembler.toResource(alerta);
	        
	        return new ResponseEntity<AlertaResource>(resource, HttpStatus.OK);
    	}
    	catch(Exception ex) {
	        return new ResponseEntity<AlertaResource>(HttpStatus.INTERNAL_SERVER_ERROR);	        
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
