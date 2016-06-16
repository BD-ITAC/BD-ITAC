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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.ita.bditac.model.AlertaDAO;
import br.ita.bditac.model.Categoria;
import br.ita.bditac.ws.support.CategoriaResource;
import br.ita.bditac.ws.support.CategoriaResourceAssembler;
import br.ita.bditac.ws.support.CategoriaResources;
import br.ita.bditac.ws.support.MessageResource;
import br.ita.bditac.ws.support.MessageResourceAssembler;


@RestController
@ExposesResourceFor(Categoria.class)
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@RequestMapping("/categorias")
public class CategoriaController {

	public interface Request {
		
	}
	
	@Autowired
	private CategoriaResourceAssembler resourceAssembler;
	
	@Autowired
	private MessageResourceAssembler messageResourceAssembler;
	
	@RequestMapping(method = RequestMethod.GET, produces = { MediaTypes.HAL_JSON_VALUE })
	public ResponseEntity<CategoriaResources> categorias() {
		try {
			List<Categoria> categorias = AlertaDAO.obterCategorias();
			List<CategoriaResource> resources = resourceAssembler.toResources(categorias);
			CategoriaResources categoriaResources = new CategoriaResources(resources);
			if(categoriaResources.getContent().size() == 0) {
				return new ResponseEntity<CategoriaResources>(HttpStatus.NOT_FOUND);
			}
			else {
				return new ResponseEntity<CategoriaResources>(categoriaResources, HttpStatus.OK);
			}
    	}
    	catch(Exception ex) {
	        return new ResponseEntity<CategoriaResources>(HttpStatus.INTERNAL_SERVER_ERROR);	        
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
