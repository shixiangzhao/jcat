package com.shixzh.spring.jcat.beanadapter;

public class PathCouldNotBeFoundException extends BeanAdapterException {

    private static final long serialVersionUID = 1L;

    PathCouldNotBeFoundException(String message) {
        super(message, null);
    }

    PathCouldNotBeFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
