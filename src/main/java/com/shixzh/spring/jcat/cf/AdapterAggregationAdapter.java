package com.shixzh.spring.jcat.cf;

import java.util.Set;

import com.shixzh.spring.jcat.spi.ConfigurationData;
import com.shixzh.spring.jcat.spi.ConfigurationFacadeAdapter;

/**
 * This class acts as a composite of all provided adapters. In other
 * words, calling any of the methods in this class will result in a call
 * to the corresponding method for each underlying adapter.
 */
class AdapterAggregationAdapter implements ConfigurationFacadeAdapter {
    AdapterAggregationAdapter() {
        /* package private */
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
