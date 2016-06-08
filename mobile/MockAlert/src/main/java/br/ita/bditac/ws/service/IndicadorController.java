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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.ita.bditac.model.AlertaDAO;
import br.ita.bditac.model.Indicador;
import br.ita.bditac.ws.support.IndicadorResource;
import br.ita.bditac.ws.support.IndicadorResourceAssembler;
import br.ita.bditac.ws.support.IndicadorResources;
import br.ita.bditac.ws.support.MessageResource;
import br.ita.bditac.ws.support.MessageResourceAssembler;

@RestController
@ExposesResourceFor(Indicador.class)
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@RequestMapping("/indicadores")
public class IndicadorController {

    private static final String COORD_REGEX = "(?:[-+]?(?:(?:[1-8]?\\d(?:\\.\\d+))+|90))";
    
    private static final String DIST_REGEX = "[+-]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*)(?:[eE][+-]?\\d+)?";
    
    public interface Request {
        
        String BY_REGIAO = "/latitude/{latitude:" + COORD_REGEX + "}" + "/longitude/{longitude:" + COORD_REGEX + "}" + "/raio/{raio:" + DIST_REGEX + "}";

    }

	@Autowired
	private IndicadorResourceAssembler resourceAssembler;

	@Autowired
	private MessageResourceAssembler messageResourceAssembler;
	
	@RequestMapping(method = RequestMethod.GET, produces = { MediaTypes.HAL_JSON_VALUE }, value = Request.BY_REGIAO)
	public ResponseEntity<IndicadorResources> indicadores(@PathVariable("latitude") String latitude, @PathVariable("longitude") String longitude, @PathVariable("raio") String raio) {
        Double dLatitude = Double.valueOf(latitude);
        Double dLongitude = Double.valueOf(longitude);
        Double dRaio = Double.valueOf(raio);
        
		try {
			List<Indicador> indicadores = AlertaDAO.obterIndicadores(dLatitude, dLongitude, dRaio);
			List<IndicadorResource> resources = resourceAssembler.toResources(indicadores);
			IndicadorResources indicadorResources = new IndicadorResources(resources);
			if(indicadorResources.getContent().size() == 0) {
				return new ResponseEntity<IndicadorResources>(HttpStatus.NOT_FOUND);
			}
			else {
				return new ResponseEntity<IndicadorResources>(indicadorResources, HttpStatus.OK);
			}
    	}
    	catch(Exception ex) {
	        return new ResponseEntity<IndicadorResources>(HttpStatus.INTERNAL_SERVER_ERROR);	        
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
