package com.shixzh.spring.jcat.msr.interfaces;

import com.shixzh.spring.jcat.spi.ConfigurationData;

public interface SuiteParameters extends ConfigurationData {

    String getRetainCreatedCv();

    boolean getCleanupLogsFlag();
}
