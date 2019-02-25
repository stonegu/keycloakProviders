package com.bizislife.keycloak.spi.impl;

import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import com.bizislife.keycloak.spi.CompanyService;
import com.bizislife.keycloak.spi.CompanyServiceProviderFactory;

public class CompanyServiceProviderFactoryImpl implements CompanyServiceProviderFactory{

	@Override
	public CompanyService create(KeycloakSession session) {
		return new CompanyServiceImpl(session);
	}

	@Override
	public void init(Scope config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postInit(KeycloakSessionFactory factory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getId() {
		return "companyServiceImpl";
	}

}
