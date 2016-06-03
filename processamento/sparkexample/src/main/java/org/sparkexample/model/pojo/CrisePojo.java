package org.sparkexample.model.pojo;

import java.io.Serializable;
import java.text.MessageFormat;

public class CrisePojo implements Serializable {
	
	private Integer id;
	private String name;

	public CrisePojo() {
	}

	public CrisePojo(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return MessageFormat.format("Crise'{'id={0}, name=''{1}'''}'", id, name);
	}
}
