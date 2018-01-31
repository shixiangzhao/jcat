package com.shixzh.spring.jcat.beanadapter;

public class DuplicatedBeanIdException extends BeanAdapterException {

    private static final long serialVersionUID = 1L;

    DuplicatedBeanIdException(String message) {
        super(message, null);
    }

    DuplicatedBeanIdException(String message, Throwable e) {
        super(message, e);
    }

}
