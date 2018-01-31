package com.shixzh.spring.jcat.files;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;

public final class FileOperations {

    public static Collection<File> listFilesRecursively(final File directory) {
        return listFilesRecursively(directory, null);
    }

    public static Collection<File> listFilesRecursively(final File directory,
            final FilenameFilter filter) {
        final Collection<File> files = new ArrayList<File>();
        final File[] entries = directory.listFiles();
        for (final File entry : entries) {
            // If there is no filter or the filter accepts the
            // file/directory, add it to the list
            if ((null == filter) || filter.accept(directory, entry.getName())) {
                files.add(entry);
            }
            if (entry.isDirectory()) {
                files.addAll(listFilesRecursively(entry, filter));
            }
        }
        return files;
    }
}
