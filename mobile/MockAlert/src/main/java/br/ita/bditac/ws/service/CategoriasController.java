package br.ita.bditac.ws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.ita.bditac.model.AlertaDAO;
import br.ita.bditac.model.Categorias;
import br.ita.bditac.ws.support.CategoriasResource;
import br.ita.bditac.ws.support.CategoriasResourceAssembler;
import br.ita.bditac.ws.support.MessageResource;
import br.ita.bditac.ws.support.MessageResourceAssembler;


@RestController
@ExposesResourceFor(Categorias.class)
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@RequestMapping("/categorias")
public class CategoriasController {

	public interface Request {
		
	}
	
	@Autowired
	private CategoriasResourceAssembler resourceAssembler;
	
	@Autowired
	private MessageResourceAssembler messageResourceAssembler;
	
	@RequestMapping(method = RequestMethod.GET, produces = { MediaTypes.HAL_JSON_VALUE })
	public ResponseEntity<CategoriasResource> categorias() {
		Categorias categorias = AlertaDAO.obterCategorias();
		if(categorias == null) {
			return new ResponseEntity<CategoriasResource>(HttpStatus.NOT_FOUND);
		}
		else {
			CategoriasResource resource = resourceAssembler.toResource(categorias);
			
			return new ResponseEntity<CategoriasResource>(resource, HttpStatus.OK);
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
