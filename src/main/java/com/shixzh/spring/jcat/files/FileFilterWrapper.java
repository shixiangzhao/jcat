package com.shixzh.spring.jcat.files;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;

class FileFilterWrapper implements FileFilterAdapter {

    private final IOFileFilter fileFilter;

    FileFilterWrapper(IOFileFilter filter) {
        if (filter == null) {
            throw new IllegalArgumentException(
                    "The IOFileFilter must not be null");
        }
        fileFilter = filter;
    }

    @Override
    public boolean accept(File file) {
        return fileFilter.accept(file);
    }

    @Override
    public boolean accept(File dir, String name) {
        return fileFilter.accept(dir, name);
    }

}
