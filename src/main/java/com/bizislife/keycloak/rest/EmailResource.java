package com.bizislife.keycloak.rest;

import com.bizislife.keycloak.model.rep.PermittedEmailRep;
import com.bizislife.keycloak.rest.exception.Error;
import com.bizislife.keycloak.spi.PermittedEmailProvider;
import com.bizislife.keycloak.util.AuthenticateUtil;
import com.bizislife.keycloak.util.GeneralUtil;
import com.bizislife.keycloak.util.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.managers.AuthenticationManager;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

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
		Validator.ValidationResult result;

		if (!(result = Validator.validateAddPermittedEmail(permittedEmail)).isPassed()) {
			long timestamp = new Date().getTime();
			return Response.status(Response.Status.PRECONDITION_FAILED).entity(new Error(timestamp, result.getMessage())).build();
		}
		if (!(result = Validator.isActionForPermittedEmailAllowed(auth, permittedEmail)).isPassed()) {
			long timestamp = new Date().getTime();
			return Response.status(Response.Status.FORBIDDEN).entity(new Error(timestamp, result.getMessage())).build();
		}
		try {
			PermittedEmailRep email = session.getProvider(PermittedEmailProvider.class).addPermittedEmail(auth.getSession().getRealm(), permittedEmail.getId(), permittedEmail.getEmail());
			return Response.ok(email).build();
		} catch (Exception e) {
			long timestamp = new Date().getTime();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Error(timestamp, e.getMessage())).build();
		}

	}

	
}
