package com.shixzh.spring.jcat.spi;

public interface ConfigurationData {

    String getId();

    Class<? extends ConfigurationData> getType();
}
