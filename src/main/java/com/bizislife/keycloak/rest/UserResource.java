package com.bizislife.keycloak.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bizislife.keycloak.model.rep.PermittedEmailRep;
import com.bizislife.keycloak.model.rep.ProspectingUserRep;
import com.bizislife.keycloak.rest.exception.Error;
import com.bizislife.keycloak.spi.PermittedEmailProvider;
import com.bizislife.keycloak.spi.ProspectingUserProvider;
import com.bizislife.keycloak.util.AuthenticateUtil;
import com.bizislife.keycloak.util.GeneralUtil;
import com.bizislife.keycloak.util.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.models.KeycloakSession;
import org.keycloak.representations.idm.UserRepresentation;

import com.bizislife.keycloak.model.rep.CompanyRepresentation;
import com.bizislife.keycloak.spi.CompanyService;
import org.keycloak.services.managers.AuthenticationManager;

@Slf4j
public class UserResource {
	private final KeycloakSession session;
	private final AuthenticationManager.AuthResult auth;

	public UserResource(KeycloakSession session, AuthenticationManager.AuthResult auth) {
		this.session = session;
		this.auth = auth;
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
    
    // this is for test spi (company)
    @GET
    @Path("companies")
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public List<CompanyRepresentation> getCompanyList(@HeaderParam("Authorization") String token) {
    	return session.getProvider(CompanyService.class).listCompanies();
    }

    @POST
	@Path("prospecting")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response addProspectingUser(@HeaderParam(HttpHeaders.AUTHORIZATION) String token,
												 ProspectingUserRep prospectingUser
												 ) {
		Validator.ValidationResult result;
		if (!(result = Validator.validateAddProspectingUser(prospectingUser)).isPassed()) {
			long timestamp = new Date().getTime();
			log.error(String.format("validateAddProspectingUser failed (errorId : %d) for email %s in %s", timestamp, prospectingUser.getEmail(), prospectingUser.getRealmId()));
			return Response.status(Response.Status.PRECONDITION_FAILED).entity(new Error(timestamp, result.getMessage())).build();
		}
		if (!(result = Validator.isAddProspectingUserAllowed(prospectingUser, session)).isPassed()) {
			long timestamp = new Date().getTime();
			log.error(String.format("isAddProspectingUserAllowed failed (errorId : %d) for email %s in %s", timestamp, prospectingUser.getEmail(), prospectingUser.getRealmId()));
			return Response.status(Response.Status.PRECONDITION_FAILED).entity(new Error(timestamp, result.getMessage())).build();
		}
		if (!(result = Validator.isActionForProspectingAllowed(auth, prospectingUser)).isPassed()) {
			long timestamp = new Date().getTime();
			log.error(String.format("isActionForProspectingAllowed failed (errorId : %d) for email %s in %s", timestamp, prospectingUser.getEmail(), prospectingUser.getRealmId()));
			return Response.status(Response.Status.FORBIDDEN).entity(new Error(timestamp, result.getMessage())).build();
		}
		try {
			ProspectingUserRep user = session.getProvider(ProspectingUserProvider.class).addProspectingUser(auth.getSession().getRealm(), prospectingUser.getId(), prospectingUser.getEmail());
			return Response.ok(user).build();
		} catch (Exception e) {
			long timestamp = new Date().getTime();
			log.error(String.format("ProspectingUserProvider.addProspectingUser failed (errorId : %d) for reason %s", timestamp, e.getMessage()), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Error(timestamp, e.getMessage())).build();
		}

	}

}
