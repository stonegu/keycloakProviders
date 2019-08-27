package com.bizislife.keycloak.spi;

import com.bizislife.keycloak.model.rep.ProspectingUserRep;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.Provider;


public interface ProspectingUserProvider extends Provider {
    ProspectingUserRep addProspectingUser(RealmModel realm, String id, String email);
}
