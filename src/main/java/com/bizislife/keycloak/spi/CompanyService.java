package com.bizislife.keycloak.spi;

import java.util.List;

import org.keycloak.provider.Provider;

import com.bizislife.keycloak.model.rep.CompanyRepresentation;

public interface CompanyService extends Provider{
    List<CompanyRepresentation> listCompanies();
}
