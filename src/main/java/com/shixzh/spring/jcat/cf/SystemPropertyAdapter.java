package com.shixzh.spring.jcat.cf;

import java.util.Set;

import com.shixzh.spring.jcat.spi.ConfigurationData;
import com.shixzh.spring.jcat.spi.ConfigurationFacadeAdapter;

/**
 * A adapter for configuration-facade that is used for configuration data handling.
 * It uses typical java system properties as configurationData.
 * <ul>
 * <li>Examples:
 * <li>Argument to the JVM: -DPREFIX.ID=type=x,host=1.1.1.1
 * <li>Definition from code: System.setProperty("PREFIX.ID","type=x,host=1.1.1.1");
 * <li>Its possible to define have lists and relations to other ConfigurationData:
 * -DPREFIX.ID=type=SomeType,stringList=abc,integerList={1,2,3},SubConfigurationDataList={ID1,ID2,
 * ID3}
 * </ul>
 * 
 */
public class SystemPropertyAdapter implements ConfigurationFacadeAdapter {
    
    private ConfigurationFacadeAdapter aggregationAdapter;
    
    @Override
    public boolean contains(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <T extends ConfigurationData> T get(Class<T> c, String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> getAllIds() {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * The aggregationAdapter used for determining if a dummy configurationData should be created
     * to be part of the aggregated ConfigurationData,
     * this allows SystemProptertyAdapter to be Writable runtime and will allow you do modify
     * ConfigurationData runtime.
     * 
     * @param aggregationAdapter
     */
    public void setAggregationAdapter(AggregationAdapter aggregationAdapter) {
        this.aggregationAdapter = aggregationAdapter;
    }

}
