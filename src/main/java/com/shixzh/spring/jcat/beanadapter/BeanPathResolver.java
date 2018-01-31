package com.shixzh.spring.jcat.beanadapter;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.shixzh.spring.jcat.files.FileFilters;
import com.shixzh.spring.jcat.files.FileOperations;

class BeanPathResolver {

    private static final String HTTP_PREFIX = "http:";
    private static final String FILE_PREFIX = "file:";
    private static final String CLASSPATH_PREFIX = "classpath:";

    //Will extend folder to file list and handle classpath files and folders.
    String[] resolve(List<String> beanPaths) {
        Set<String> resolvedBeanPaths = new LinkedHashSet<String>();
        for (String path : fixBeanPaths(beanPaths)) {
            if (path.contains("${")) {
                resolvedBeanPaths.add(path);
                continue;
            }
            List<String> expandClasspathPaths = expandClasspathFolders(path);
            if (!expandClasspathPaths.isEmpty()) {
                resolvedBeanPaths.addAll(expandClasspathPaths);
            }
            List<String> expandFilePaths = expandFilePaths(path);
            if (!expandFilePaths.isEmpty()) {
                resolvedBeanPaths.addAll(expandFilePaths);
            }
        }
        return resolvedBeanPaths.toArray(new String[resolvedBeanPaths.size()]);
    }

    private List<String> expandFilePaths(String path) {
        List<String> list = new ArrayList<String>();
        if (path.startsWith(FILE_PREFIX)) {
            File file = new File(path.replaceFirst(FILE_PREFIX, ""));
            if (file.isDirectory()) {
                Collection<File> files = FileOperations.listFilesRecursively(file, FileFilters.extension("xml"));
                for (File xml : files) {
                    list.add(FILE_PREFIX + xml.getAbsolutePath());
                }
            } else {
                list.add(path);
            }
        }
        return list;
    }

    private List<String> expandClasspathFolders(String path) {
        if (path.startsWith(CLASSPATH_PREFIX)) {
            Path myPath = createPath(path);
            String prefix = FILE_PREFIX;
            if (isInJar(path)) {
                prefix = CLASSPATH_PREFIX;
            }
            return getFilesRecursivly(myPath, prefix);
        }
        return Collections.emptyList();
    }

    private List<String> getFilesRecursivly(Path myPath, final String prefix) {
        final List<String> pathList = new ArrayList<String>();
        try {
            // 遍历myPath文件夹的所有文件
            Files.walkFileTree(myPath, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(".xml")) {
                        pathList.add(prefix + file.toString());
                    }
                    return super.visitFile(file, attrs);
                }
            });
        } catch (IOException e) {
            throw new PathCouldNotBeFoundException("Was not able to traverse the file tree in jar. Path: \"" + myPath
                    + "\"", e);
        }
        return pathList;
    }

    private Path createPath(String path) {
        URI uri = getURIofPath(path);
        if (isInJar(path)) { //目录包含jar
            String key = uri.toString().replaceFirst("!.*", "");
            // TODO
            return null;
        } else {
            return Paths.get(uri);
        }
    }

    private boolean isInJar(String path) {
        return getURIofPath(path).getScheme().equals("jar");
    }

    private URI getURIofPath(String path) {
        String newPath = path.replaceFirst(CLASSPATH_PREFIX + "[/]?", "/");
        //configurationdata/msran-test-capacity/test_parameters
        URL resource = BeanAdapter.class.getResource(newPath);
        if (resource == null) {
            throw new PathCouldNotBeFoundException("Path: \"" + path
                    + "\" could not be found, Make sure it does really exist!");
        }
        try {
            return resource.toURI();
        } catch (URISyntaxException e) {
            throw new PathCouldNotBeFoundException("Path: \"" + path
                    + "\" seems to be malformed.", e);
        }
    }

    private Set<String> fixBeanPaths(List<String> beanPaths) {
        Set<String> beanPathSet = new LinkedHashSet<String>();
        for (String path : beanPaths) {
            if (path.startsWith(CLASSPATH_PREFIX) || path.startsWith(FILE_PREFIX) || path.startsWith(HTTP_PREFIX)) {
                beanPathSet.add(path);
            } else {
                beanPathSet.add(FILE_PREFIX + path);
            }
        }
        return beanPathSet;
    }

}
