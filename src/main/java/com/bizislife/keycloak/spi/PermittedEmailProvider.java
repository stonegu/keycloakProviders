package com.bizislife.keycloak.spi;

import com.bizislife.keycloak.model.rep.PermittedEmailRep;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.Provider;

import java.util.List;


public interface PermittedEmailProvider extends Provider {
    PermittedEmailRep addPermittedEmail(RealmModel realm, String id, String email);
    List<PermittedEmailRep> listEmail(String realmId);
}
