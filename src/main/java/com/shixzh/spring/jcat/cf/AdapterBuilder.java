package com.shixzh.spring.jcat.cf;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shixzh.spring.jcat.spi.ConfigurationFacadeAdapter;
import com.shixzh.spring.jcat.spi.ConfigurationFacadeAdapterProvider;

public class AdapterBuilder {

    private final static String NULL_ARGUMENT = "Illegal null argument!";
    private static Logger logger = LoggerFactory.getLogger(AdapterBuilder.class);
    private boolean configurationDataAggregationEnabled = false;

    private final List<ConfigurationFacadeAdapterProvider> providers = new ArrayList<>();
    private boolean returnValueAnnotationEnabled = false;
    private boolean decryptionEnabled;
    private String secretKeyPath;
    private String encryptedPropertiesPath;

    public AdapterBuilder addProvider(ConfigurationFacadeAdapterProvider provider) {
        if (provider == null) {
            throw new NullPointerException(NULL_ARGUMENT);
        }
        addToInternalProviderList(provider);
        return this;

    }

    private void addToInternalProviderList(ConfigurationFacadeAdapterProvider provider) {
        if (!providers.contains(provider)) {
            providers.add(provider);
        } else {
            logger.debug("duplicate ConfigurationFacadeAdapterProvider ignored: " + provider);
        }
    }

    public AdapterBuilder setReturnValueAnnotationEnabled(Boolean enabled) {
        this.returnValueAnnotationEnabled = enabled;
        return this;
    }

    /**
     * build() 可以根据标志位创建对象
     * @return
     */
    public ConfigurationFacadeAdapter build() {
        if (providers.isEmpty()) {
            logger.debug(AggregationAdapter.class.getName() + " constructed with no added providers.");
        }
        // 默认是 new AggregationAdapter,如果其他类没有实现，再回到这里使用默认的。
        ConfigurationFacadeAdapter adapter = new AggregationAdapter(providers, configurationDataAggregationEnabled);

        if (returnValueAnnotationEnabled) {
            adapter = new ReturnValueAnnotationAdapter(adapter);
        }
        if (decryptionEnabled) {
            adapter = new DecryptionAdapter(adapter, secretKeyPath, encryptedPropertiesPath);
        }
        return adapter;
    }
}
