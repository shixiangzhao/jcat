package com.shixzh.spring.jcat.beanadapter;

import java.lang.reflect.Modifier;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

import com.shixzh.spring.jcat.proxy.Proxy;

final class CustomDefaultListableBeanFactory extends DefaultListableBeanFactory {

    private final String packageNameForModelClasses;

    CustomDefaultListableBeanFactory(BeanFactory parentBeanFactory,
            String packageNameForModelClasses) {
        super(parentBeanFactory);
        this.packageNameForModelClasses = packageNameForModelClasses;
        setAllowBeanDefinitionOverriding(false);
    }

    @Override
    protected Object createBean(final String beanName,
            final RootBeanDefinition rbd, final Object[] args) {
        Class<?> type = getClassOrFigureOutClass(beanName, rbd);
        rbd.setBeanClass(type);
        if (!isClassInterfaceOrAbstract(type)) {
            return super.createBean(beanName, rbd, args);
        }
        @SuppressWarnings("unused")
        final Object proxy = Proxy.javaBean(rbd.getBeanClass(), true);
        
        return args;

    }

    private boolean isClassInterfaceOrAbstract(Class<?> clazz) {
        return clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers());
    }

    private Class<?> getClassOrFigureOutClass(final String beanName,
            final RootBeanDefinition mbd) {
        String className = mbd.getBeanClassName();
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName(packageNameForModelClasses + "."
                        + className);
            } catch (ClassNotFoundException e1) {
                throw new CannotLoadBeanClassException(
                        mbd.getResourceDescription(), beanName,
                        mbd.getBeanClassName(), e1);
            }
        }
    }
}
