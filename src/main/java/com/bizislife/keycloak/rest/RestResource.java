package com.bizislife.keycloak.rest;

import javax.ws.rs.Path;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager;

import com.bizislife.keycloak.util.AuthenticateUtil;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.ForbiddenException;

@Slf4j
public class RestResource {
	private final KeycloakSession session;
    private final AuthenticationManager.AuthResult auth;

	public RestResource(KeycloakSession session) {
		this.session = session;
        this.auth = new AppAuthManager().authenticateBearerToken(session, session.getContext().getRealm());

	}
	
	@Path("company")
	public CompanyResource getCompanyResourceAuthenticated() {
		if (AuthenticateUtil.isBizUser(auth) && AuthenticateUtil.hasAdminRealmRole(auth)) {
			return new CompanyResource(session);
		} else {
			log.error("User is not Biz user and/or doesnot have admin role");
			throw new ForbiddenException("User is not Biz user and/or doesnot have admin role");
		}
		
	}
	
	@Path("user")
	public UserResource getUserResourceAuthenticated() {
		if (AuthenticateUtil.hasAdminRealmRole(auth) || AuthenticateUtil.hasUserRealmRole(auth) || AuthenticateUtil.hasClientRealmRole(auth)) {
			return new UserResource(session, auth);
		} else {
			log.error("User doesnot have client or admin role");
			throw new ForbiddenException("Does not have client or admin role");
		}
	}
	
	@Path("token")
	public TokenResource getTokenResourceAuthenticated() {
		if (AuthenticateUtil.hasAdminRealmRole(auth) || AuthenticateUtil.hasUserRealmRole(auth) || AuthenticateUtil.hasGuestRealmRole(auth)) {
			return new TokenResource(session);
		} else {
			log.error("User doesnot have client or admin or guest role");
			throw new ForbiddenException("Does not have client or admin or guest role");
		}
	}
	
	
	
}
