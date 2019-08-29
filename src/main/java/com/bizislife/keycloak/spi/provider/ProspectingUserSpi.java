package com.bizislife.keycloak.spi.provider;

import com.bizislife.keycloak.spi.ProspectingUserProvider;
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
        return "prospectingUser";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return ProspectingUserProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return ProspectingUserProviderFactory.class;
    }
}
