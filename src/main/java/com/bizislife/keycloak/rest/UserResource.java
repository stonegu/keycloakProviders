package com.bizislife.keycloak.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bizislife.keycloak.model.rep.PermittedEmailRep;
import com.bizislife.keycloak.model.rep.ProspectingUserRep;
import com.bizislife.keycloak.spi.PermittedEmailProvider;
import com.bizislife.keycloak.spi.ProspectingUserProvider;
import com.bizislife.keycloak.util.AuthenticateUtil;
import com.bizislife.keycloak.util.GeneralUtil;
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
		if (!validateAddProspectingUser(prospectingUser)) {
			log.error(String.format("validateAddProspectingUser failed for email %s in %s", prospectingUser.getEmail(), prospectingUser.getRealmId()));
			return Response.status(Response.Status.PRECONDITION_FAILED).build();
		}
		if (!isAddProspectingUserAllowed(prospectingUser)) {
			log.error(String.format("isAddProspectingUserAllowed failed for email %s in %s"), prospectingUser.getEmail(), prospectingUser.getRealmId());
			return Response.status(Response.Status.PRECONDITION_FAILED).build();
		}
		if (!isActionForProspectingAllowed(auth, prospectingUser)) {
			log.error(String.format("isActionForProspectingAllowed failed for email %s in %s"), prospectingUser.getEmail(), prospectingUser.getRealmId());
			return Response.status(Response.Status.FORBIDDEN).build();
		}
		try {
			ProspectingUserRep user = session.getProvider(ProspectingUserProvider.class).addProspectingUser(auth.getSession().getRealm(), prospectingUser.getId(), prospectingUser.getEmail());
			return Response.ok(user).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

	}

	// only biz admin user or client account with same same realm Id can do it
	private boolean isActionForProspectingAllowed(AuthenticationManager.AuthResult auth, ProspectingUserRep prospectingUser) {
		if ( (AuthenticateUtil.isBizUser(auth) && AuthenticateUtil.hasAdminRealmRole(auth)) ||
				(AuthenticateUtil.hasClientRealmRole(auth) && auth.getSession().getRealm().getId().equals(prospectingUser.getRealmId()))) {
			return true;
		}
		return false;
	}

	private boolean validateAddProspectingUser(ProspectingUserRep user) {
		return StringUtils.isNotEmpty(user.getRealmId()) && GeneralUtil.emailValidate(user.getEmail());
	}

	private boolean isAddProspectingUserAllowed(ProspectingUserRep prospectingUser) {
		List<PermittedEmailRep> permittedEmailList = session.getProvider(PermittedEmailProvider.class).listEmail(prospectingUser.getRealmId());
		if (CollectionUtils.isEmpty(permittedEmailList)) { // not specific settings
			return true;
		} else { // has permission settings
			return permittedEmailList.stream().parallel().filter(permittedEmailRep -> permittedEmailRep.getEmail().equals(prospectingUser.getEmail())).count() > 0;
		}
	}

	
	
}
