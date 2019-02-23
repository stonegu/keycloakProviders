package com.bizislife.keycloak.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.models.KeycloakSession;
import org.keycloak.representations.idm.UserRepresentation;

public class UserResource {
	private final KeycloakSession session;

	public UserResource(KeycloakSession session) {
		this.session = session;
	}
	

    @GET
    @Path("")
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchUserByAttribute(
    		@QueryParam("attributeName") String attributeName, 
    		@QueryParam("attributeValue") String attributeValue,
    		@QueryParam("activatedOnly") boolean activatedOnly,
    		@HeaderParam("Authorization") String token) {
    	
    	List<UserRepresentation> users = new ArrayList<UserRepresentation>();
    	UserRepresentation user = new UserRepresentation();
    	user.setFirstName("stone");
    	user.setLastName("gu");
    	user.setId("1111");
    	users.add(user);
    	
    	return Response.ok(users).build();
    	
    }
	
	
	
}
