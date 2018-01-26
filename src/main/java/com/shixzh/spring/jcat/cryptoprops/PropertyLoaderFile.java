package com.shixzh.spring.jcat.cryptoprops;

import java.io.File;

final class PropertyLoaderFile extends PropertyLoader {

    private File file;
    
    public PropertyLoaderFile(File file) {
        this.file = file;
    }

}
