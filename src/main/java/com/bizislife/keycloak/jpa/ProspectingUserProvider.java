package com.bizislife.keycloak.jpa;

import com.bizislife.keycloak.jpa.pojo.ProspectingUser;
import com.bizislife.keycloak.model.ProspectingUserRep;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.Provider;


public interface ProspectingUserProvider extends Provider {
    ProspectingUserRep addProspectingUser(RealmModel realm, String id, String email);
}
