package com.bizislife.keycloak.model.pojo.provider;

import com.bizislife.keycloak.jpa.CompanyEntityProvider;
import org.keycloak.Config.Scope;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class ProspectingUserEntityProviderFactory implements JpaEntityProviderFactory{

	@Override
	public JpaEntityProvider create(KeycloakSession session) {
		return new ProspectingUserEntityProvider(session);
	}

	@Override
	public void init(Scope config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postInit(KeycloakSessionFactory factory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getId() {
		return "prospecting-user-entity-provider";
	}

}
