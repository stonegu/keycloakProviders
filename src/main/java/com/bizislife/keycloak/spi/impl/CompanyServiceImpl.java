package com.bizislife.keycloak.spi.impl;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

import com.bizislife.keycloak.model.CompanyRepresentation;
import com.bizislife.keycloak.spi.CompanyService;

public class CompanyServiceImpl implements CompanyService{
    private final KeycloakSession session;

	public CompanyServiceImpl(KeycloakSession session) {
		this.session = session;
		if (getRealm() == null) {
            throw new IllegalStateException("The service cannot accept a session without a realm in its context.");
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CompanyRepresentation> listCompanies() {
		List<CompanyRepresentation> reps = new ArrayList<>();
		CompanyRepresentation rep = new CompanyRepresentation();
		rep.setId("id1");
		rep.setName("name1");
		reps.add(rep);
		return reps;
	}
	
    protected RealmModel getRealm() {
        return session.getContext().getRealm();
    }
	

}
