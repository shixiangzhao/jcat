package com.shixzh.spring.jcat.cryptoprops;

public final class CryptoFactory {

    private CryptoFactory() {

    }

    public static CryptoPropertiesBuilder newCryptoPropertiesBuilder() {
        return new CryptoPropertiesBuilder();
    }
}
