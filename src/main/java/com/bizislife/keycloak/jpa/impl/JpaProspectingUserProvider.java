package com.bizislife.keycloak.jpa.impl;

import com.bizislife.keycloak.jpa.ProspectingUserProvider;
import com.bizislife.keycloak.jpa.pojo.ProspectingUser;
import com.bizislife.keycloak.model.ProspectingUserRep;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.utils.KeycloakModelUtils;

import javax.persistence.EntityManager;

public class JpaProspectingUserProvider implements ProspectingUserProvider {

    private final KeycloakSession session;
    protected EntityManager em;

    public JpaProspectingUserProvider(KeycloakSession session, EntityManager em) {
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
