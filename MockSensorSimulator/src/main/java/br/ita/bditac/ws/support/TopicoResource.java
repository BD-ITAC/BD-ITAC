package br.ita.bditac.ws.support;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import br.ita.bditac.model.Topico;

public class TopicoResource extends Resource<Topico> {

	public TopicoResource() {
		super(new Topico());
	}
	
	public TopicoResource(Topico topico) {
		super(topico);
	}
	
	public TopicoResource(Topico topico, Iterable<Link> links) {
		super(topico, links);
	}
	
	public TopicoResource(Topico topico, Link... links) {
		super(topico, links);
	}
	
}
