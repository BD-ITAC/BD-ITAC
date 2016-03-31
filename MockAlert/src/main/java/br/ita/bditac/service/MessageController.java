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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.ita.bditac.model.AlertaDAO;
import br.ita.bditac.model.Message;
import br.ita.bditac.support.MessageResource;
import br.ita.bditac.support.MessageResourceAssembler;

@RestController
@ExposesResourceFor(Message.class)
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@RequestMapping("/message")
public class MessageController {

    public interface Request {

        String ID = "/{id}";

    }

    @Autowired
    private MessageResourceAssembler resourceAssembler;

    @Autowired
    private AlertaDAO service;

    @RequestMapping(method = RequestMethod.OPTIONS, produces = { MediaTypes.HAL_JSON_VALUE })
    public ResponseEntity<MessageResource> options(@RequestHeader(value = "Version", required = false) String version) {
        MessageResource resource = resourceAssembler.toResource(2, "Not implemented");

        return new ResponseEntity<MessageResource>(resource, HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(method = RequestMethod.GET, produces = { MediaTypes.HAL_JSON_VALUE })
    public ResponseEntity<List<MessageResource>> messages(@RequestHeader(value = "Version", required = false) String version) {
        List<Message> messages = service.obterMessages();
        List<MessageResource> resource = resourceAssembler.toResources(messages);

        return new ResponseEntity<List<MessageResource>>(resource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = { MediaTypes.HAL_JSON_VALUE }, value = Request.ID)
    public ResponseEntity<MessageResource> message(@PathVariable("id") int id) {
        Message message = service.obterMessage(id);
        MessageResource resource = resourceAssembler.toResource(message);

        return new ResponseEntity<MessageResource>(resource, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<MessageResource> handleExceptions(Exception ex) {
        MessageResource resource = resourceAssembler.toResource(2, ex.getMessage());

        return new ResponseEntity<MessageResource>(resource, HttpStatus.BAD_REQUEST);
    }

}
