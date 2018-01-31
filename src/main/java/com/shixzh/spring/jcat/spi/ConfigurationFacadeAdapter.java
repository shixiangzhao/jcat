package com.shixzh.spring.jcat.spi;

import java.util.Set;

public interface ConfigurationFacadeAdapter {

    boolean contains(String id);

    <T extends ConfigurationData> T get(Class<T> c, String id);

    Set<String> getAllIds();
}
