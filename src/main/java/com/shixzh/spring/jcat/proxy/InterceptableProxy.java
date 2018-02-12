package com.shixzh.spring.jcat.proxy;

import java.util.List;

import org.aopalliance.intercept.Interceptor;

public interface InterceptableProxy {

    List<Interceptor> getInterceptorList();

}
