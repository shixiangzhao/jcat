package com.shixzh.spring.jcat.cf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shixzh.spring.jcat.spi.ConfigurationData;
import com.shixzh.spring.jcat.spi.ConfigurationFacadeAdapter;
import com.shixzh.spring.jcat.spi.ConfigurationFacadeAdapterProvider;

public class AggregationAdapter implements ConfigurationFacadeAdapter {

    private static final String THIS_SHOULD_NOT_HAPPEN_MESSAGE = "This should not happen!";
    final List<ConfigurationFacadeAdapterProvider> providers;
    private final boolean configurationDataAggregationEnabled;
    private static final Logger logger = LoggerFactory.getLogger(AggregationAdapter.class);

    private ConfigurationFacadeAdapter adapterComposite;

    AggregationAdapter(List<ConfigurationFacadeAdapterProvider> providers,
            boolean configurationDataAggregationEnabled) {
        this.providers = providers;
        this.configurationDataAggregationEnabled = configurationDataAggregationEnabled;
    }

    @Override
    public boolean contains(String id) {
        if (id == null) {
            throw new NullPointerException("Argument is null!");
        }
        return false;
    }

    @Override
    public <T extends ConfigurationData> T get(Class<T> c, String id) {
        if (c == null) {
            throw new NullPointerException("Argument 'c' is null!");
        }
        if (id == null) {
            throw new NullPointerException("Argument 'id' is null!");
        }
        return getAdapterComposite().get(c, id);
    }

    @Override
    public Set<String> getAllIds() {
        logger.debug("Use default adapter.");
        return getAdapterComposite().getAllIds();
    }

    /**
     * The first call to this method will initialize all provided adapters
     * and return them in an adapter-composite. Sub-sequenced calls will
     * return the same object.
     * 
     * @return - an adapter composite, containing initialized.
     * @throws IllegalStateException if any of the underlying adapters are null.
     */
    private synchronized ConfigurationFacadeAdapter getAdapterComposite() {
        if (adapterComposite == null) {
            initilizeAllProvidedAdapters();
        }
        return adapterComposite;
    }

    void initilizeAllProvidedAdapters() {
        if (configurationDataAggregationEnabled) {
            adapterComposite = new ConfigurationDataAggregationAdapter();
        } else {
            adapterComposite = new AdapterAggregationAdapter();
        }
        if (providers.isEmpty()) {
            return;
        }

        for (final ConfigurationFacadeAdapterProvider provider : providers) {
            ConfigurationFacadeAdapter adapter = provider.initializeAndGetAdapter();
            throwExceptionIfNullAdapter(adapter, provider);

            if (configurationDataAggregationEnabled && adapter instanceof SystemPropertyAdapter) {
                ((SystemPropertyAdapter) adapter).setAggregationAdapter(this);
            }
            addAdapterToUnderlyingComposite(adapter);
        }

    }

    private void addAdapterToUnderlyingComposite(ConfigurationFacadeAdapter adapter) {
        Method method;
        try {
            method = adapterComposite.getClass().getMethod("add", ConfigurationFacadeAdapter.class);
            try {
                method.invoke(adapterComposite, adapter);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(THIS_SHOULD_NOT_HAPPEN_MESSAGE, e);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(THIS_SHOULD_NOT_HAPPEN_MESSAGE, e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(THIS_SHOULD_NOT_HAPPEN_MESSAGE, e);
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(THIS_SHOULD_NOT_HAPPEN_MESSAGE, e);
        } catch (SecurityException e) {
            throw new RuntimeException(THIS_SHOULD_NOT_HAPPEN_MESSAGE, e);
        }
    }

    private void throwExceptionIfNullAdapter(Object n,
            ConfigurationFacadeAdapterProvider provider) {
        String message = "Illegal value 'null' returned from method 'initializeAndGetAdapter()' by underlying provider: %s";
        message = String.format(message, provider);
        throw new IllegalStateException(message);
    }

}
