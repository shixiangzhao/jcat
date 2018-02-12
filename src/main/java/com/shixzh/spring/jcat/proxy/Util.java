package com.shixzh.spring.jcat.proxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public final class Util {

    private Util() {
        //hidden
    }

    public static boolean isToStringOrHashcodeOrEqualsMethod(Method method) {
        try {
            return Util.methodSignatureEquals(method, Object.class.getMethod("toString"))
                    ||
                    Util.methodSignatureEquals(method, Object.class.getMethod("hashCode"))
                    || Util.methodSignatureEquals(method,
                            Object.class.getMethod("equals", Object.class));
        } catch (SecurityException e) {
            throw new ProxyException("This should NEVER happen", e);
        } catch (NoSuchMethodException e) {
            throw new ProxyException("This should NEVER happen", e);
        }
    }

    public static boolean methodSignatureEquals(Method method, final Method method2) {
        boolean nameEq = method2.getName().equals(method.getName());
        return nameEq && methodParamsEquals(method, method2);
    }

    private static boolean methodParamsEquals(Method method, final Method methodsInObject) {
        return Arrays.deepEquals(methodsInObject.getParameterTypes(),
                method.getParameterTypes());
    }

}
