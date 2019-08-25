package com.bizislife.keycloak.model;

import com.bizislife.keycloak.jpa.pojo.Company;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRepresentation {
	private String id;
	private String name;
	
	public CompanyRepresentation(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public CompanyRepresentation(Company company) {
		this.id = company.getId();
		this.name = company.getName();
	}
	
}
