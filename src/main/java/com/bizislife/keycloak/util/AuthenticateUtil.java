package com.bizislife.keycloak.util;

import javax.ws.rs.NotAuthorizedException;

import org.keycloak.services.managers.AuthenticationManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticateUtil {
	
	public static boolean isAuthenticatedBearerToken(AuthenticationManager.AuthResult auth) {
		return auth != null;
	}
	
	// has guest realm role
	public static boolean hasGuestRealmRole(AuthenticationManager.AuthResult auth) {
		if (isAuthenticatedBearerToken(auth)) {
			if (auth.getToken().getRealmAccess() != null && auth.getToken().getRealmAccess().isUserInRole(Role.guest.name()) ) {
				return true;
			}
		} else {
			log.error("Token cannot be authorized!");
			throw new NotAuthorizedException("Bearer");
		}
		return false;
	}
	
	// has user realm role
	public static boolean hasUserRealmRole(AuthenticationManager.AuthResult auth) {
		if (isAuthenticatedBearerToken(auth)) {
			if (auth.getToken().getRealmAccess() != null && auth.getToken().getRealmAccess().isUserInRole(Role.user.name()) ) {
				return true;
			}
		} else {
			log.error("Token cannot be authorized!");
			throw new NotAuthorizedException("Bearer");
		}
		return false;
	}

	// has client realm role (only clients have this role)
	public static boolean hasClientRealmRole(AuthenticationManager.AuthResult auth) {
		if (isAuthenticatedBearerToken(auth)) {
			if (auth.getToken().getRealmAccess() != null && auth.getToken().getRealmAccess().isUserInRole(Role.client.name()) ) {
				return true;
			}
		} else {
			log.error("Token cannot be authorized!");
			throw new NotAuthorizedException("Bearer");
		}
		return false;
	}

	// has admin realm role
	public static boolean hasAdminRealmRole(AuthenticationManager.AuthResult auth) {
		if (isAuthenticatedBearerToken(auth)) {
			if (auth.getToken().getRealmAccess() != null && auth.getToken().getRealmAccess().isUserInRole(Role.admin.name()) ) {
				return true;
			}
		} else {
			log.error("Token cannot be authorized!");
			throw new NotAuthorizedException("Bearer");
		}
		return false;
	}
	
	// users under realm Biz
	public static boolean isBizUser(AuthenticationManager.AuthResult auth) {
		if (isAuthenticatedBearerToken(auth)) {
			return auth.getSession().getRealm().getName().equals(Realm.BIZ.name());
		} else {
			log.error("Token cannot be authorized!");
			throw new NotAuthorizedException("Bearer");
		}
	}
	
	

}
