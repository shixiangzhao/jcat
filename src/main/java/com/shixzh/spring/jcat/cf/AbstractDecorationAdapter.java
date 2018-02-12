package com.shixzh.spring.jcat.cf;

import java.util.Set;

import com.shixzh.spring.jcat.proxy.Interceptor;
import com.shixzh.spring.jcat.proxy.Invocation;
import com.shixzh.spring.jcat.spi.ConfigurationData;
import com.shixzh.spring.jcat.spi.ConfigurationFacadeAdapter;

public abstract class AbstractDecorationAdapter implements ConfigurationFacadeAdapter {

    private final ConfigurationFacadeAdapter adapter;
    private final Interceptor interceptor = new InterceptorDecoration();

    private final class InterceptorDecoration implements Interceptor {

        @Override
        public Object intercept(Invocation invocation) throws Throwable {
            //TODO
            return null;
        }

    }

    public AbstractDecorationAdapter(ConfigurationFacadeAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public Set<String> getAllIds() {
        return adapter.getAllIds();
    }

    @Override
    public boolean contains(String id) {
        return adapter.contains(id);
    }

    public <T extends ConfigurationData> T get(Class<T> c, String id) {
        @SuppressWarnings("unused")
        T configData = adapter.get(c, id);
        return null;
        //return withInterceptor(configData);
    }

    //    private <T> T withInterceptor(T singleConfigurationData) {
    //        if (singleConfigurationData instanceof InterceptableProxy) {
    //            if (Proxy.getProxyInterface(singleConfigurationData).getInterceptorList().contains(interceptor)) {
    //                return singleConfigurationData;
    //            }
    //        }
    //        return Proxy.intercept(singleConfigurationData, interceptor);
    //    }

}
