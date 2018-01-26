package com.shixzh.spring.jcat.cf;

import com.shixzh.spring.jcat.spi.ConfigurationData;
import com.shixzh.spring.jcat.spi.ConfigurationFacadeAdapter;

class ReturnValueAnnotationAdapter extends AbstractDecorationAdapter {

    ReturnValueAnnotationAdapter(ConfigurationFacadeAdapter adapter) {
        super(adapter);
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
}
