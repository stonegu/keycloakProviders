package com.bizislife.keycloak.spi;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

public class ProspectingUserSpi implements Spi {
    @Override
    public boolean isInternal() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return null;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return null;
    }
}
