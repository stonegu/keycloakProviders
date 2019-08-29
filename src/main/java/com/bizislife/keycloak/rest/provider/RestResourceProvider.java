package com.bizislife.keycloak.rest.provider;

import com.bizislife.keycloak.rest.RestResource;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class RestResourceProvider implements RealmResourceProvider {
    private KeycloakSession session;


	public RestResourceProvider(KeycloakSession session) {
		this.session = session;
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

	public Object getResource() {
		return new RestResource(session);
	}

}
