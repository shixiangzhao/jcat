package com.shixzh.spring.jcat;

import java.util.List;

import com.shixzh.spring.jcat.beanadapter.BeanAdapterProvider;

public class BeanValidationTest {

    public void validateDir(List<String> dataPathList) {
        BeanAdapterProvider provider = new BeanAdapterProvider(dataPathList);
    }
}
