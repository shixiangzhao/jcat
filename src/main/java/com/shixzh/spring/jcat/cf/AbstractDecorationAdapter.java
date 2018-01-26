package com.shixzh.spring.jcat.cf;

import java.util.Set;

import com.shixzh.spring.jcat.spi.ConfigurationFacadeAdapter;

public abstract class AbstractDecorationAdapter implements ConfigurationFacadeAdapter {

    private final ConfigurationFacadeAdapter adapter;

    public AbstractDecorationAdapter(ConfigurationFacadeAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public Set<String> getAllIds() {
        return adapter.getAllIds();
    }
}
