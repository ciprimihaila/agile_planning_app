package com.mycompany.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "configurations")
public class Configuration {
	public Configuration() {
	}

	public Configuration(String label, int index, String name, Object value) {
		this.label = label;
		this.index = index;
		this.name = name;
		this.value = value;
	}

	private String label;
	private int index;
	private String name;
	private Object value;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
