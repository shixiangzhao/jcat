package com.shixzh.spring.jcat.cf;

import java.util.Set;

import com.shixzh.spring.jcat.cryptoprops.CryptoFactory;
import com.shixzh.spring.jcat.cryptoprops.CryptoProperties;
import com.shixzh.spring.jcat.spi.ConfigurationData;
import com.shixzh.spring.jcat.spi.ConfigurationFacadeAdapter;

class DecryptionAdapter extends AbstractDecorationAdapter {

    private final CryptoProperties crypto;

    DecryptionAdapter(ConfigurationFacadeAdapter adapter, final String keyPath, 
            final String propsPath) {
        super(adapter);
        this.crypto = CryptoFactory.newCryptoPropertiesBuilder()
                .loadSecretKeyFromPath(keyPath)
                .loadPropertiesFromPath(propsPath).build();
    }

    @Override
    public boolean contains(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <T extends ConfigurationData> T get(Class<T> c, String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> getAllIds() {
        // TODO Auto-generated method stub
        return null;
    }
}
