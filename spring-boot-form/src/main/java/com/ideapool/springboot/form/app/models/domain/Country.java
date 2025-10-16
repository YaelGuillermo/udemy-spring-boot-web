package com.ideapool.springboot.form.app.models.domain;

public class Country {

	private Integer id;	
	private String code;
	private String name;

	public Country(Integer id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public Country() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.id.toString();
	}
}
