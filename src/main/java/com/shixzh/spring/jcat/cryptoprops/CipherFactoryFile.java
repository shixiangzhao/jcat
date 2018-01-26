package com.shixzh.spring.jcat.cryptoprops;

import java.io.File;

final class CipherFactoryFile extends CipherFactory {

    private File file;
    
    public CipherFactoryFile(File file) {
        this.file = file;
    }

}
