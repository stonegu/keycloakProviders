package com.bizislife.keycloak.jpa.impl;

import com.bizislife.keycloak.jpa.ProspectingUserProvider;
import com.bizislife.keycloak.jpa.ProspectingUserProviderFactory;
import org.keycloak.Config;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import javax.persistence.EntityManager;

public class JpaProspectingUserProviderFactory implements ProspectingUserProviderFactory {
    @Override
    public ProspectingUserProvider create(KeycloakSession session) {
        EntityManager em = session.getProvider(JpaConnectionProvider.class).getEntityManager();
        return new JpaProspectingUserProvider(session, em);
    }

    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "jpa";
    }
}
