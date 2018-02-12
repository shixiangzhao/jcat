package com.shixzh.spring.jcat.beanadapter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanIsAbstractException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.shixzh.spring.jcat.spi.ConfigurationData;
import com.shixzh.spring.jcat.spi.ConfigurationFacadeAdapter;

public class BeanAdapter implements ConfigurationFacadeAdapter {

    private final CustomXmlApplicationContext context;
    private Set<String> allIdSet = null;

    public BeanAdapter(List<String> beanFilePathList, final Class<? extends ConfigurationData> modelClass) {
        BeanPathResolver pathFixer = new BeanPathResolver();
        context = new CustomXmlApplicationContext(pathFixer.resolve(beanFilePathList), modelClass);
        context.registerShutdownHook();
    }

    @Override
    public boolean contains(String id) {
        return getAllIds().contains(id);
    }

    @Override
    public <T extends ConfigurationData> T get(Class<T> type, String id) {
        try {
            Object bean = context.getBean(id);
            if (type.isAssignableFrom(bean.getClass())) {
                return (T) bean;
            }else {
                String message = "No '%s' with id '%s' exists in the %s. The type of is probably mismatching with the asked for type";
                message = String.format(message, type, id, this.getClass().getSimpleName());
                throw new IllegalArgumentException(message);
            }
        }  catch (NoSuchBeanDefinitionException e) {
            String message = "No '%s' with id '%s' exists in the %s. Couldn't find that id at all";
            message = String.format(message, type, id, this.getClass().getSimpleName());
            throw new IllegalArgumentException(message, e);
        } catch (BeansException e) {
            String message = "Failed to instantiate '%s' with id '%s' in the %s";
            message = String.format(message, type, id, this.getClass().getSimpleName());
            throw new IllegalStateException(message, e);
        }
    }

    @Override
    public Set<String> getAllIds() {
        if (allIdSet == null) {
            allIdSet = initAllIdsSet();

        }
        return allIdSet;
    }

    private Set<String> initAllIdsSet() {
        String[] beanNames = context.getBeanDefinitionNames();
        Set<String> set = new LinkedHashSet<String>();
        for (String id : beanNames) {
            if (context.isTypeMatch(id, ConfigurationData.class)) {
                try {
                    context.getBean(id); //TODO no other way to check if abstract=true?
                    set.add(id);
                } catch (BeanIsAbstractException e) {
                    //just check next.
                }
            }
        }
        return null;
    }

}
