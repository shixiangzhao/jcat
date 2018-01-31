package com.shixzh.spring.jcat.files;

import org.apache.commons.io.filefilter.SuffixFileFilter;

public final class FileFilters {

    public static FileFilterAdapter extension(final String fileExtension) {
        return new FileFilterWrapper(new SuffixFileFilter("." + fileExtension));
    }
}
