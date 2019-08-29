package com.bizislife.keycloak.spi.impl.provider;

import com.bizislife.keycloak.spi.PermittedEmailProvider;
import com.bizislife.keycloak.spi.impl.PermittedEmailProviderImpl;
import com.bizislife.keycloak.spi.provider.PermittedEmailProviderFactory;
import org.keycloak.Config;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import javax.persistence.EntityManager;

public class PermittedEmailProviderFactoryImpl implements PermittedEmailProviderFactory {
    @Override
    public PermittedEmailProvider create(KeycloakSession session) {
        EntityManager em = session.getProvider(JpaConnectionProvider.class).getEntityManager();
        return new PermittedEmailProviderImpl(session, em);
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
        return "permittedEmailProviderImpl";
    }
}
