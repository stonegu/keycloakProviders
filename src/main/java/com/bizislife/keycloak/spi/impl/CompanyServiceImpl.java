package com.bizislife.keycloak.spi.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.collections.CollectionUtils;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

import com.bizislife.keycloak.jpa.pojo.Company;
import com.bizislife.keycloak.model.rep.CompanyRepresentation;
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
		List<Company> companyEntities = getEntityManager().createNamedQuery("findByRealm", Company.class)
				.setParameter("realmId", getRealm().getId())
				.getResultList();
		
		List<CompanyRepresentation> reps = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(companyEntities)) {
			for (Company company : companyEntities) {
				reps.add(new CompanyRepresentation(company));
			}
		}
		return reps;
	}
	
    protected RealmModel getRealm() {
        return session.getContext().getRealm();
    }
    
    private EntityManager getEntityManager() {
    	return session.getProvider(JpaConnectionProvider.class).getEntityManager();
    }
	

}
