package com.shixzh.spring.jcat.beanadapter;

import java.io.FileNotFoundException;
import java.util.Arrays;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.parsing.BeanDefinitionParsingException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.shixzh.spring.jcat.spi.ConfigurationData;

final class CustomXmlApplicationContext extends ClassPathXmlApplicationContext {

    private final Class<? extends ConfigurationData> modelClass;

    CustomXmlApplicationContext(String[] beanFilePaths,
            Class<? extends ConfigurationData> modelClass) {
        super(beanFilePaths, false);
        this.modelClass = modelClass;
        try {
            this.refresh();
        } catch (BeanDefinitionParsingException e) {
            throw new DuplicatedBeanIdException("Probably duplicated bean ids or corrupt XML file(s)", e);
        } catch (BeanDefinitionStoreException e) {
            if (e.getCause() instanceof FileNotFoundException) {
                throw new PathCouldNotBeFoundException("A path in:" + Arrays.toString(beanFilePaths)
                        + " could not be found, Make sure it does really exist!", e);
            }
            throw e;
        }
    }
    
    /**
     * 该方法用于指定创建bean的工厂，这里指定自定义的CustomDefaultListableBeanFactory
     * 
     */
    @Override
    protected DefaultListableBeanFactory createBeanFactory() {
        String packageName = "";
        if (modelClass != null) {
            packageName = modelClass.getPackage().getName();
        }
        return new CustomDefaultListableBeanFactory(getInternalParentBeanFactory(), packageName);
    }
}
