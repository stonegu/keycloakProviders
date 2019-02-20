package com.bizislife.keycloak.rest;

import org.keycloak.models.KeycloakSession;

public class RestResource {
	private final KeycloakSession session;

	public RestResource(KeycloakSession session) {
		this.session = session;
	}

}
