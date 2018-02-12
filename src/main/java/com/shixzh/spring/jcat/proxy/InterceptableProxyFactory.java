package com.shixzh.spring.jcat.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

public class InterceptableProxyFactory {

    private static final String ADDITIONAL_METHODS_SUFFIX = "ExtendedByProxy";
    private static final Logger LOG = LoggerFactory
            .getLogger(InterceptableProxyFactory.class);

    private enum ProxyType {
        INTERFACE, CLASS, OBJECT
    }

    private static final MethodFilter METHOD_FILTER = new MethodFilter() {

        @Override
        public boolean isHandled(final Method method) {
            // if not overridable Object.class method
            if (method.getDeclaringClass().equals(Object.class)
                    && !Util.isToStringOrHashcodeOrEqualsMethod(method)) {
                return false;
            }
            return true;
        }
    };

    private final ProxyFactory factory = new ProxyFactory();
    private final ProxyType type;

    public InterceptableProxyFactory(ProxyType type) {
        this.type = type;
        factory.setFilter(METHOD_FILTER);
    }

    public static <T> T createANewInterfaceJavaBeanProxy(Class<T> toJavaBeanify) {
        return createANewInterfaceProxy(addAdditionalSetMethodsToClass(toJavaBeanify));
    }

    static <T> T createANewInterfaceProxy(
            Class<?>... interfaces) {
        InterceptableProxyFactory builder = new InterceptableProxyFactory(ProxyType.INTERFACE);
        builder.setInterfaces(filterOnlyAccessableInterfaces(interfaces[0], interfaces));
        return builder.build();
    }

    <T> T build() {
        try {
            return createProxyObject(type);
        } catch (Exception e) {
            throw new ProxyException("Not able to create proxy", e);
        }
    }

    private <T> T createProxyObject(ProxyType type2) {
        // TODO Auto-generated method stub
        return null;
    }

    void setInterfaces(Class<?>... interfaces) {
        factory.setInterfaces(makeAValidInterfaceArray(interfaces));
    }

    private static Class<?>[] makeAValidInterfaceArray(Class<?>... interfaces) {
        // makes a ordered set of the interfaces. (removes duplicates)
        Set<Class<?>> set = new LinkedHashSet<Class<?>>(Arrays.asList(interfaces));
        set.remove(InterceptableProxy.class); // remove is exists
        set.add(InterceptableProxy.class); // needed by this library.
        set.remove(ProxyObject.class); // not allowed by javassit.
        set.remove(ProxyObject.class); // not allowed by javassit.
        return set.toArray(new Class<?>[set.size()]);
    }

    private static Class<?>[] filterOnlyAccessableInterfaces(final Class<?> classToIntercept, Class<?>... interfaces) {
        List<Class<?>> newClassList = new ArrayList<Class<?>>();
        for (Class<?> inter : interfaces) {
            int modifiers = inter.getModifiers();
            boolean accessable = Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers);
            if (accessable || classToIntercept.getPackage().equals(inter.getPackage())) {
                newClassList.add(inter);
            }
        }
        return newClassList.toArray(new Class<?>[newClassList.size()]);
    }

    @SuppressWarnings("unchecked")
    public static <T> T createANewClassJavaBeanProxy(
            Class<?> toJavaBeanify, Class<?>... interfaces) {
        Class<?> modifiedClass = addAdditionalSetMethodsToClass(toJavaBeanify);
        return (T) createANewClassProxy(modifiedClass, interfaces);
    }

    private static Class<?> addAdditionalSetMethodsToClass(Class<?> javaBean) {
        CtClass cc = null;
        try {
            return Class.forName(javaBean.getCanonicalName() + ADDITIONAL_METHODS_SUFFIX);
        } catch (ClassNotFoundException e) {
            LOG.trace(javaBean.getCanonicalName() + ADDITIONAL_METHODS_SUFFIX + " did not exist. Creates one");
            cc = createNewClass(javaBean);
            addAdditionalSetMethodsTo(cc);
        } catch (RuntimeException e) { //(workaround) Powermocks classloader throws runtimeException.
            if (e.getCause() != null && e.getCause() instanceof NotFoundException) {
                LOG.trace(javaBean.getCanonicalName() + ADDITIONAL_METHODS_SUFFIX + " did not exist. Creates one");
                cc = createNewClass(javaBean);
                addAdditionalSetMethodsTo(cc);
            }
        }
        try {
            return cc.toClass();
        } catch (CannotCompileException e) {
            LOG.warn(
                    "Was not able to create new proxy class. Will use the provided one instead which wont have the additional methods.",
                    e);
        }
        return javaBean;
    }

    /**
     * 根据接口的方法名，向CtClass增加方法体
     * 
     * @param classToAddMethodTo
     */
    private static void addAdditionalSetMethodsTo(CtClass classToAddMethodTo) {
        for (CtMethod method : classToAddMethodTo.getMethods()) {
            String methodName = method.getName();
            if ((methodName.startsWith("get") || methodName.startsWith("is"))
                    && !methodName.equals("getClass")) {
                String newMethodName = methodName.replaceFirst("get|is", "set");
                CtMethod newMethod = null;
                try {
                    CtClass[] param = new CtClass[] { method.getReturnType() };
                    newMethod = CtNewMethod.abstractMethod(
                            ClassPool.getDefault().get(Void.class.getCanonicalName()), newMethodName, param, null,
                            classToAddMethodTo);
                } catch (NotFoundException e) {
                    throw new ProxyException("This should NEVER happen", e);
                }
                try {
                    classToAddMethodTo.addMethod(newMethod);
                } catch (CannotCompileException e) {
                    LOG.debug("Was not able to add a new method to proxy class, method: " + newMethodName);
                    LOG.trace("Reason why it was not able to add method:", e);
                }
            }
        }
    }

    private static CtClass createNewClass(Class<?> javaBean) {
        final ClassPool pool = ClassPool.getDefault();
        CtClass cc = null;
        try {
            cc = pool.get(getJavaBeanClassName(javaBean));
            cc.setName(javaBean.getCanonicalName() + ADDITIONAL_METHODS_SUFFIX);
        } catch (final NotFoundException e) {
            throw new ProxyException("This should NEVER happen", e);
        }
        try {
            cc.setSuperclass(pool.get(getJavaBeanClassName(javaBean)));
        } catch (CannotCompileException e) {
            throw new ProxyException("Was not able to create new proxy class", e);
        } catch (NotFoundException e) {
            throw new ProxyException("This should NEVER happen", e);
        }
        return cc;
    }

    private static String getJavaBeanClassName(Class<?> javaBean) {
        String className = javaBean.getCanonicalName();
        //如果存在同名的class，则在名字后面加$short name
        if (javaBean.getEnclosingClass() != null) {
            className = javaBean.getEnclosingClass().getCanonicalName() + "$" + javaBean.getSimpleName();
        }
        return className;
    }

    private static <T> T createANewClassProxy(Class<?> modifiedClass, Class<?>[] interfaces) {
        // TODO Auto-generated method stub
        return null;
    }

    private static Class<?> s(Class<?> toJavaBeanify) {
        // TODO Auto-generated method stub
        return null;
    }

}
