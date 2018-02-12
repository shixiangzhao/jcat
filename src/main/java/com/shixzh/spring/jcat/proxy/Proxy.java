package com.shixzh.spring.jcat.proxy;

import java.lang.reflect.Modifier;

public final class Proxy<T> {

    public static InterceptableProxy getProxyInterface(Object proxy) {
        if (proxy instanceof InterceptableProxy) {
            return (InterceptableProxy) proxy;
        }
        throw new IllegalArgumentException(
                "Not possible to get the proxy interface of a non proxy object");
    }

    @SuppressWarnings("unused")
    public static <T> T javaBean(Class<T> classToProxy, boolean primitiveDefaultIsException) {
        if (!classToProxy.isInterface()) {
            if (Modifier.isAbstract(classToProxy.getModifiers())) {
                T proxy = InterceptableProxyFactory.createANewClassJavaBeanProxy(classToProxy);
                
            }
        }
        T proxy = InterceptableProxyFactory.createANewInterfaceJavaBeanProxy(classToProxy);
        addInterceptor(proxy, new InterceptorJavaBean(proxy.getClass(), primitiveDefaultIsException));
        return null;
    }

    private static <T> void addInterceptor(T proxy, InterceptorJavaBean interceptorJavaBean) {
        // TODO Auto-generated method stub
        
    }

}
