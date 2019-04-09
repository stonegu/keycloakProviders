package com.bizislife.keycloak.rest;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

import com.bizislife.keycloak.util.AuthenticateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompanyResource {
	private final KeycloakSession session;

	public CompanyResource(KeycloakSession session) {
		super();
		this.session = session;
	}

	
    @POST
    @Path("")
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRealm(
    		@HeaderParam("Authorization") String token) {
    	String uuid = UUID.randomUUID().toString();
    	
//    	RealmModel realm = session.realms().createRealm(uuid, uuid);
    	return Response.ok().build();
    }
	
}
