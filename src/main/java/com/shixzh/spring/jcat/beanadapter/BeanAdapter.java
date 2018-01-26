package com.shixzh.spring.jcat.beanadapter;

import java.util.List;
import java.util.Set;

import com.shixzh.spring.jcat.spi.ConfigurationData;
import com.shixzh.spring.jcat.spi.ConfigurationFacadeAdapter;

public class BeanAdapter implements ConfigurationFacadeAdapter {

    private final CustomXmlApplicationContext context;

    public BeanAdapter(List<String> beanFilePathList, final Class<? extends ConfigurationData> modelClass) {
        BeanPathResolver pathFixer = new BeanPathResolver();
        context = new CustomXmlApplicationContext(pathFixer.resolve(beanFilePathList), modelClass);
        context.registerShutdownHook();
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
