package com.bizislife.keycloak.spi.impl;

import com.bizislife.keycloak.model.pojo.PermittedEmail;
import com.bizislife.keycloak.model.rep.PermittedEmailRep;
import com.bizislife.keycloak.spi.PermittedEmailProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.utils.KeycloakModelUtils;

import javax.persistence.EntityManager;

public class PermittedEmailProviderImpl implements PermittedEmailProvider {

    private final KeycloakSession session;
    protected EntityManager em;

    public PermittedEmailProviderImpl(KeycloakSession session, EntityManager em) {
        this.session = session;
        this.em = em;
    }

    @Override
    public PermittedEmailRep addPermittedEmail(RealmModel realm, String id, String email) {
        if (id == null) {
            id = KeycloakModelUtils.generateId();
        }

        PermittedEmail permittedEmail = new PermittedEmail();
        permittedEmail.setId(id);
        permittedEmail.setEmail(email);
        permittedEmail.setCreatedTimestamp(System.currentTimeMillis());
        permittedEmail.setReamId(realm.getId());

        em.persist(permittedEmail);
        em.flush();

        return new PermittedEmailRep(permittedEmail);
    }

    @Override
    public void close() {

    }
}
