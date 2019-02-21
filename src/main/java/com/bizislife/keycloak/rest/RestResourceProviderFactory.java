package com.bizislife.keycloak.rest;

import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

public class RestResourceProviderFactory implements RealmResourceProviderFactory{

    public static final String ID = "rest";

	public RealmResourceProvider create(KeycloakSession session) {
		return new RestResourceProvider(session);
	}

	public void init(Scope config) {
		// TODO Auto-generated method stub
		
	}

	public void postInit(KeycloakSessionFactory factory) {
		// TODO Auto-generated method stub
		
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

	public String getId() {
		return ID;
	}

}
