package com.shixzh.spring.jcat;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shixzh.spring.jcat.beanadapter.BeanAdapterProvider;
import com.shixzh.spring.jcat.cf.ConfigurationFacade;
import com.shixzh.spring.jcat.model.LteSimResource;
import com.shixzh.spring.jcat.msr.interfaces.SuiteParameters;
import com.shixzh.spring.jcat.spi.ConfigurationFacadeAdapter;

public class BeanValidationTest {

    private static Logger logger = LoggerFactory.getLogger(BeanValidationTest.class);

    public static void main(String[] args) {
        //validateVerdictConfiguration();
        testGetSuite();
    }

    public static void validateVerdictConfiguration() {
        List<String> dataPathList = new ArrayList<String>();
        dataPathList.add("classpath:configurationdata/msran-test-performance/test_parameters");
        validateDir(dataPathList);
    }

    private static void validateDir(List<String> dataPathList) {
        BeanAdapterProvider provider = new BeanAdapterProvider(dataPathList);
        provider.setModelClassToFetchDefaultPackageFrom(LteSimResource.class);

        ConfigurationFacadeAdapter configurationDataDB = ConfigurationFacade.newAdapterBuilder()
                .addProvider(provider)
                .setReturnValueAnnotationEnabled(true)
                .build();
        logger.info("\nLoading " + configurationDataDB.getAllIds().size() + " beans");
    }

    public static void testGetSuite() {
        List<String> dataPathList = new ArrayList<String>();
        dataPathList
                .add("classpath:configurationdata/msran-test-performance/test_parameters/Cap_DUS41_FDD_12cell_20BW.xml");
        //set the prefix to "resource." and default package to where SomeConfigurationDataClass is. 
        BeanAdapterProvider provider = new BeanAdapterProvider(dataPathList);
        provider.setModelClassToFetchDefaultPackageFrom(LteSimResource.class);

        ConfigurationFacadeAdapter configurationDataDB = ConfigurationFacade.newAdapterBuilder()
                .addProvider(provider)
                .setReturnValueAnnotationEnabled(true)
                .build();
        SuiteParameters sp = configurationDataDB.get(SuiteParameters.class, "Cap_DUS41_FDD_12cell_20BW");
        System.out.println(sp.getRetainCreatedCv());
    }
}
