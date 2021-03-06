package com.bizislife.keycloak.spi.provider;

import com.bizislife.keycloak.spi.PermittedEmailProvider;
import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

public class PermittedEmailSpi implements Spi {
    @Override
    public boolean isInternal() {
        return false;
    }

    @Override
    public String getName() {
        return "permittedEmail";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return PermittedEmailProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return PermittedEmailProviderFactory.class;
    }
}
