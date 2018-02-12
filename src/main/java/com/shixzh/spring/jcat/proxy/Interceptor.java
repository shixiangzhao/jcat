package com.shixzh.spring.jcat.proxy;

import java.io.Serializable;

public interface Interceptor extends Serializable {
    Object intercept(Invocation invocation) throws Throwable;
}
