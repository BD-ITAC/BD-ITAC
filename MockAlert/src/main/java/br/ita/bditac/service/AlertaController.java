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
import br.ita.bditac.model.AlertaService;
import br.ita.bditac.support.AlertaResource;
import br.ita.bditac.support.AlertaResourceAssembler;
import br.ita.bditac.support.MessageResource;
import br.ita.bditac.support.MessageResourceAssembler;

@RestController
@ExposesResourceFor(Alerta.class)
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@RequestMapping("/alerta")
public class AlertaController {

    public interface Request {
        
        String ID = "/{id}";
        
        //String BY_REGIAO = "/latitude/{latitude:^(\\-?\\d+(\\.\\d+)?),\\s*(\\-?\\d+(\\.\\d+)?)$}/longitude/{longitude:^(\\-?\\d+(\\.\\d+)?),\\s*(\\-?\\d+(\\.\\d+)?)$}/raio/{raio:^(\\-?\\d+(\\.\\d+)?),\\s*(\\-?\\d+(\\.\\d+)?)$}";
        String BY_REGIAO = "/latitude/{latitude}/longitude/{longitude}/raio/{raio}";

    }

    @Autowired
    private AlertaResourceAssembler resourceAssembler;

    @Autowired
    private MessageResourceAssembler messageResourceAssembler;

    @Autowired
    private AlertaService service;

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaTypes.HAL_JSON_VALUE }, produces = { MediaTypes.HAL_JSON_VALUE })
    public ResponseEntity<AlertaResource> adicionar(@RequestBody Alerta body) {
        Alerta alerta = service.adicionarAlerta(body);
        AlertaResource resource = resourceAssembler.toResource(alerta);
        
        return new ResponseEntity<AlertaResource>(resource, HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = { MediaTypes.HAL_JSON_VALUE }, value = Request.ID)
    public ResponseEntity<AlertaResource> alerta(@PathVariable("id") int id) {
        Alerta alerta = service.obterAlerta(id);
        AlertaResource resource = resourceAssembler.toResource(alerta);
        
        return new ResponseEntity<AlertaResource>(resource, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = { MediaTypes.HAL_JSON_VALUE }, value = Request.BY_REGIAO)
    public ResponseEntity<List<AlertaResource>> alertasPorRegiao(@PathVariable("latitude") String latitude, @PathVariable("longitude") String longitude, @PathVariable("raio") String raio) {
        double dLatitude = Double.parseDouble(latitude);
        double dLongitude = Double.parseDouble(longitude);
        double dRaio = Double.parseDouble(raio);
        
        List<Alerta> alertas = service.obterAlertasPorRegiao(dLatitude, dLongitude, dRaio);
        List<AlertaResource> resource = resourceAssembler.toResources(alertas);
        
        return new ResponseEntity<List<AlertaResource>>(resource, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<MessageResource> handleExceptions(Exception ex) {
        MessageResource resource = messageResourceAssembler.toResource(1, ex.getMessage());

        return new ResponseEntity<MessageResource>(resource, HttpStatus.BAD_REQUEST);
    }

}
