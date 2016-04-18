package br.ita.bditac.service;

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

import br.ita.bditac.model.AlertaDAO;
import br.ita.bditac.model.Indicadores;
import br.ita.bditac.support.IndicadoresResource;
import br.ita.bditac.support.IndicadoresResourceAssembler;
import br.ita.bditac.support.MessageResource;
import br.ita.bditac.support.MessageResourceAssembler;

@RestController
@ExposesResourceFor(Indicadores.class)
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@RequestMapping("/indicadores")
public class IndicadoresController {

	@Autowired
	private IndicadoresResourceAssembler resourceAssembler;

	@Autowired
	private MessageResourceAssembler messageResourceAssembler;
	
	@Autowired
	private AlertaDAO service;
	
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaTypes.HAL_JSON_VALUE }, produces = { MediaTypes.HAL_JSON_VALUE })
	public ResponseEntity<IndicadoresResource> adicionar(@RequestBody Indicadores body) {
		Indicadores indicadores = service.adicionarIndicadores(body);
		IndicadoresResource resource = resourceAssembler.toResource(indicadores);
		
		return new ResponseEntity<IndicadoresResource>(resource, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = { MediaTypes.HAL_JSON_VALUE })
	public ResponseEntity<IndicadoresResource> indicadores() {
		Indicadores indicadores = service.obterIndicadores();
		if(indicadores == null) {
			return new ResponseEntity<IndicadoresResource>(HttpStatus.NOT_FOUND);
		}
		else {
			IndicadoresResource resource = resourceAssembler.toResource(indicadores);
			
			return new ResponseEntity<IndicadoresResource>(resource, HttpStatus.OK);
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
