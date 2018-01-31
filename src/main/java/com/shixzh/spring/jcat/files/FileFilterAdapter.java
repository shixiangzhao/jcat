package com.shixzh.spring.jcat.files;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public interface FileFilterAdapter extends FileFilter, FilenameFilter {

    @Override
    boolean accept(final File file);

    @Override
    boolean accept(final File dir, final String name);
}
