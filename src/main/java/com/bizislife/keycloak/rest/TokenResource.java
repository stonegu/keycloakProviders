package com.bizislife.keycloak.rest;

import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.models.KeycloakSession;

public class TokenResource {
	private final KeycloakSession session;

	public TokenResource(KeycloakSession session) {
		this.session = session;
	}
	
	@HEAD
	@Path("verify")
    @NoCache
    public Response tokenVarify(@HeaderParam("Authorization") String token) {
		return Response.ok().build();
	}
	

}
