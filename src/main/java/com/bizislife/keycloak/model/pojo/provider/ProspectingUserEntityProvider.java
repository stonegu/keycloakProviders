package com.bizislife.keycloak.model.pojo.provider;

import com.bizislife.keycloak.jpa.pojo.Company;
import com.bizislife.keycloak.model.pojo.ProspectingUser;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;
import org.keycloak.models.KeycloakSession;

import java.util.Collections;
import java.util.List;

public class ProspectingUserEntityProvider implements JpaEntityProvider{

	private KeycloakSession session;

	public ProspectingUserEntityProvider(KeycloakSession session) {
		this.session = session;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Class<?>> getEntities() {
		return Collections.<Class<?>>singletonList(ProspectingUser.class);
	}
	
    // This is used to return the location of the Liquibase changelog file.
    // You can return null if you don't want Liquibase to create and update the DB schema.
	@Override
	public String getChangelogLocation() {
		return null;
	}

	@Override
	public String getFactoryId() {
		// TODO Auto-generated method stub
		return null;
	}

}
