package com.bizislife.keycloak.rest;

import javax.ws.rs.Path;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;

public class RestResource {
	private final KeycloakSession session;
    private final AuthenticationManager.AuthResult auth;

	public RestResource(KeycloakSession session) {
		this.session = session;
        this.auth = new AppAuthManager().authenticateBearerToken(session, session.getContext().getRealm());
	}
	
	@Path("users")
	public UserResource getUserResource() {
		return new UserResource(session);
	}
	
	@Path("users-auth")
	public UserResource getUserResourceAuthenticated() {
        checkRealmAdmin();
		return new UserResource(session);
	}
	
    private void checkRealmAdmin() {
        if (auth == null) {
            throw new NotAuthorizedException("Bearer");
        } else if (auth.getToken().getRealmAccess() == null || !auth.getToken().getRealmAccess().isUserInRole("admin")) {
            throw new ForbiddenException("Does not have realm admin role");
        }
    }
}
