package com.tngtech.infrastructure.util.io;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tngtech.infrastructure.exception.SystemException;

/**
 * Simple implementation of the FileLister interface.
 * Always goes to the file system to ask for the current files.
 */
public class DefaultFileLister implements FileLister {
    private String[] dirNamesToExclude;
    private static final long MAX_FILE_SIZE = 1 * 1024 * 1024; // 1 MB

    public DefaultFileLister(String[] dirNamesToExclude) {
        this.dirNamesToExclude = dirNamesToExclude;
    }

    @Override
    public List<File> listFiles(File startingPoint, String[] extensions) {
        return listFiles(new File[]{startingPoint}, extensions);
    }

    @Override
    public List<File> listFiles(File[] startingPoints, String[] extensions) {
        List<File> result = new ArrayList<File>();
        for (File startingPoint : startingPoints) {
            process(result, startingPoint, extensions);
        }
        return result;
    }

    @Override
    public List<File> listAllFiles(File startingPoint) {
        List<File> result = new ArrayList<File>();
        process(result, startingPoint);
        return result;
    }

    private void addFileIfNotTooBig(List<File> result, File f) {
        if (f.isFile() && f.length() > MAX_FILE_SIZE) {
            System.out.println("Warning: ignoring file " + f.getAbsolutePath() + ", as it is too big: " + f.length() + ", maximum supported file size is " + MAX_FILE_SIZE);
            return;
        }
        result.add(f);
    }

    @Override
    public String getCanonicalPathForSomethingReturnedByFileListerService(File f) {
        try {
            return f.getCanonicalPath();
        } catch (IOException e) {
            throw new SystemException("unable to get canonical path for file " + f.getAbsolutePath(), e);
        }
    }

    private void process(List<File> result, File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                if (acceptDir(f)) {
                    process(result, f);
                }
            } else {
                addFileIfNotTooBig(result, f);
            }
        }
    }

    private void process(List<File> result, File file, String[] extensions) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                if (acceptDir(f)) {
                    process(result, f, extensions);
                }
            } else {
                if (accept(f, extensions)) {
                    addFileIfNotTooBig(result, f);
                }
            }
        }
    }

    private boolean acceptDir(File dir) {
        String name = dir.getName();
        for (String s : dirNamesToExclude) {
            if (name.equals(s)) {
                return false;
            }
        }
        return true;
    }

    private boolean accept(File f, String[] extensions) {
        String name = f.getName();
        for (String s : extensions) {
            if (name.endsWith(s)) {
                return true;
            }
        }
        return false;
    }
}
