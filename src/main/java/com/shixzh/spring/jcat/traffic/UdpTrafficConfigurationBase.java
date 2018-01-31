package com.shixzh.spring.jcat.traffic;

import com.shixzh.spring.jcat.spi.ConfigurationData;

public interface UdpTrafficConfigurationBase extends TrafficConfiguration, ConfigurationData {

    int getDurationSec();

    //@ReturnValue(defaultsTo = "1470")
    int getDatagramSize();
}
