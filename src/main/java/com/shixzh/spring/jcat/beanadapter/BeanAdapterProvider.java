package com.shixzh.spring.jcat.beanadapter;

import java.util.ArrayList;
import java.util.List;

import com.shixzh.spring.jcat.spi.ConfigurationData;
import com.shixzh.spring.jcat.spi.ConfigurationFacadeAdapter;
import com.shixzh.spring.jcat.spi.ConfigurationFacadeAdapterProvider;

public class BeanAdapterProvider implements ConfigurationFacadeAdapterProvider {

    private final List<String> beanConfigLocations = new ArrayList<String>();
    private Class<? extends ConfigurationData> modelClass;

    public BeanAdapterProvider(List<String> dataPathList) {
        this.beanConfigLocations.addAll(dataPathList);
    }

    @Override
    public ConfigurationFacadeAdapter initializeAndGetAdapter() {
        return new BeanAdapter(beanConfigLocations, modelClass);
    }

    public void setModelClassToFetchDefaultPackageFrom(Class<? extends ConfigurationData> modelClass) {
        this.modelClass = modelClass;
    }

}
