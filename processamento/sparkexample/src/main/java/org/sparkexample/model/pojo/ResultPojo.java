package org.sparkexample.model.pojo;

import java.io.Serializable;
import java.text.MessageFormat;

public class ResultPojo implements Serializable {
	
	private Integer id;
	private String name;
	private Integer value;

	public ResultPojo() {
	}

	public ResultPojo(Integer id, String name, Integer value) {
		this.id = id;
		this.name = name;
		this.value = value;
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

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return MessageFormat.format("Tweet'{'id={0}, name=''{1}'''}'", id, name);
	}
}
