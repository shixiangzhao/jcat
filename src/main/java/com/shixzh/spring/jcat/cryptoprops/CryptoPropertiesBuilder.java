package com.shixzh.spring.jcat.cryptoprops;

import java.io.File;

public class CryptoPropertiesBuilder {

    private CipherFactory cipherFactory = new CipherFactoryNull();
    private PropertyLoader propsLoader = new PropertyLoaderNull();

    public CryptoPropertiesBuilder loadSecretKeyFromPath(String keyPath) {

        // try file first
        if (keyPath != null) {
            File file = new File(keyPath);
            if (file.exists()) {
                loadSecreKeyFromFile(file);
                return this;
            }

            // then try finding path as a resource in classpath.
            cipherFactory = new CipherFactoryClasspath(keyPath);
        }
        return this;
    }

    private CryptoPropertiesBuilder loadSecreKeyFromFile(File file) {
        if (file != null) {
            cipherFactory = new CipherFactoryFile(file);
        }
        return this;
    }

    public CryptoPropertiesBuilder loadPropertiesFromPath(String propsPath) {
        if (propsPath != null) {
            // try file first
            File file = new File(propsPath);
            if (file.exists()) {
                loadPropertiesFromFile(file);
                return this;
            }

            // then try finding path as a resource in classpath.
            propsLoader = new PropertyLoaderClasspath(propsPath);
        }
        return this;
    }

    private void loadPropertiesFromFile(File file) {
        if (file != null) {
            propsLoader = new PropertyLoaderFile(file);
        }

    }

    public CryptoProperties build() {
        // TODO Auto-generated method stub
        return null;
    }

}
