package com.bizislife.keycloak.rest;

import com.bizislife.keycloak.model.rep.PermittedEmailRep;
import com.bizislife.keycloak.spi.PermittedEmailProvider;
import com.bizislife.keycloak.util.AuthenticateUtil;
import com.bizislife.keycloak.util.GeneralUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.managers.AuthenticationManager;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
public class EmailResource {
	private final KeycloakSession session;
	private final AuthenticationManager.AuthResult auth;

	public EmailResource(KeycloakSession session, AuthenticationManager.AuthResult auth) {
		this.session = session;
		this.auth = auth;
	}

    @POST
	@Path("permitted")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response addPermittedEmail(@HeaderParam(HttpHeaders.AUTHORIZATION) String token,
												 PermittedEmailRep permittedEmail
												 ) {
		if (!validateAddPermittedEmail(permittedEmail)) {
			return Response.status(Response.Status.PRECONDITION_FAILED).build();
		}
		if (!isActionForPermittedEmailAllowed(auth, permittedEmail)) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
		try {
			PermittedEmailRep email = session.getProvider(PermittedEmailProvider.class).addPermittedEmail(auth.getSession().getRealm(), permittedEmail.getId(), permittedEmail.getEmail());
			return Response.ok(email).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

	}

	// only biz admin user or client account with same same realm Id can do it
	private boolean isActionForPermittedEmailAllowed(AuthenticationManager.AuthResult auth, PermittedEmailRep permittedEmail) {
		if ( (AuthenticateUtil.isBizUser(auth) && AuthenticateUtil.hasAdminRealmRole(auth)) ||
				(AuthenticateUtil.hasClientRealmRole(auth) && auth.getSession().getRealm().getId().equals(permittedEmail.getRealmId()))) {
			return true;
		}
		return false;
	}

	private boolean validateAddPermittedEmail(PermittedEmailRep email) {
		return StringUtils.isNotEmpty(email.getRealmId()) && GeneralUtil.emailValidate(email.getEmail());
	}

	
	
}
