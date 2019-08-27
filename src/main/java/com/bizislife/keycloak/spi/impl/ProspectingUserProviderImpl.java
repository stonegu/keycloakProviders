package com.bizislife.keycloak.spi.impl;

import com.bizislife.keycloak.spi.ProspectingUserProvider;
import com.bizislife.keycloak.model.pojo.ProspectingUser;
import com.bizislife.keycloak.model.rep.ProspectingUserRep;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.utils.KeycloakModelUtils;

import javax.persistence.EntityManager;

public class ProspectingUserProviderImpl implements ProspectingUserProvider {

    private final KeycloakSession session;
    protected EntityManager em;

    public ProspectingUserProviderImpl(KeycloakSession session, EntityManager em) {
        this.session = session;
        this.em = em;
    }

    @Override
    public ProspectingUserRep addProspectingUser(RealmModel realm, String id, String email) {
        if (id == null) {
            id = KeycloakModelUtils.generateId();
        }

        ProspectingUser prospectingUser = new ProspectingUser();
        prospectingUser.setId(id);
        prospectingUser.setEmail(email);
        prospectingUser.setCreatedTimestamp(System.currentTimeMillis());
        prospectingUser.setReamId(realm.getId());

        em.persist(prospectingUser);
        em.flush();

        return new ProspectingUserRep(prospectingUser);
    }

    @Override
    public void close() {

    }
}
