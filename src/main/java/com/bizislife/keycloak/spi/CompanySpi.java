package com.bizislife.keycloak.spi;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

public class CompanySpi implements Spi{

	@Override
	public boolean isInternal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		return "company";
	}

	@Override
	public Class<? extends Provider> getProviderClass() {
		return CompanyService.class;
	}

	@Override
    @SuppressWarnings("rawtypes")
	public Class<? extends ProviderFactory> getProviderFactoryClass() {
		return CompanyServiceProviderFactory.class;
	}

}
