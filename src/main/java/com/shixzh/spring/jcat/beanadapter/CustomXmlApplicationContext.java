package com.shixzh.spring.jcat.beanadapter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.shixzh.spring.jcat.spi.ConfigurationData;

final class CustomXmlApplicationContext extends ClassPathXmlApplicationContext {

    public CustomXmlApplicationContext(ApplicationContext resolve, Class<? extends ConfigurationData> modelClass) {
        // TODO Auto-generated constructor stub
    }

}
