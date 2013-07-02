package com.tngtech.infrastructure.util.io;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tngtech.infrastructure.exception.SystemException;

/**
 * Caching implementation of the FileLister interface.
 * When constructing an instance you have to say for which directory part you want an instance.
 * This cached data is then used to return the requested list of files.
 * This of course means that if files are added/removed after the instance is created,
 * this will not be reflected in the results.
 *
 * This means this class is mainly useful for testing environments.
 */
public class CachingFileLister implements FileLister {
    private String[] directoryNamesToExclude;
    private String[] extensionsToExclude;

    // the next two maps should be always in sync!
    private Map<String, File> fileNameToFileMap = new HashMap<String, File>();
    private Map<File, String> fileToAbsolutePathNameMap = new HashMap<File, String>();

    private Map<File, List<File>> directoryMap = new HashMap<File, List<File>>();
    private Map<File, List<File>> filesMap = new HashMap<File, List<File>>();
    private static final long MAX_FILE_SIZE = 1*1024*1024; // 1 MB

    public CachingFileLister(File startingPointDir, String[] directoryNamesToExclude, String[] extensionsToExclude) {
        this.directoryNamesToExclude = directoryNamesToExclude;
        this.extensionsToExclude = extensionsToExclude;

        String pathname = startingPointDir.getAbsolutePath();
        File startingPointDirCanonical = new File(pathname);
        processDir(startingPointDirCanonical);
        // fileNameToFileMap.put(startingPointDir.getAbsolutePath(), startingPointDir);
        fileNameToFileMap.put(pathname, startingPointDirCanonical);
        fileToAbsolutePathNameMap.put(startingPointDirCanonical, pathname);
    }

    @Override
    public List<File> listFiles(File startingPoint, String[] extensions) {
        return listFiles(new File[]{startingPoint}, extensions);
    }

    @Override
    public List<File> listFiles(File[] startingPoints, String[] extensions) {
        List<File> result = new ArrayList<File>();

        for (File startingPoint : startingPoints) {
            String absolutePath = startingPoint.getAbsolutePath();
            File internalFile = fileNameToFileMap.get(absolutePath);
            if (internalFile != null) {
                processListFilesDir(result, startingPoint, extensions);
            } else {
                // whoops, we do not know the file?
                throw new SystemException("file not known to cache, probably does not exist: " + absolutePath);
            }
        }
        return result;
    }

    @Override
    public List<File> listAllFiles(File startingPoint) {
        return listFiles(startingPoint, null);
    }

    private void addFileIfNotTooBig(List<File> result, File f) {
        if (f.isFile() && f.length() > MAX_FILE_SIZE) {
            System.out.println("Warning: ignoring file " + f.getAbsolutePath() + ", as it is too big: " + f.length() + ", maximum supported file size is " + MAX_FILE_SIZE);
            return;
        }
        result.add(f);
    }

    private void processListFilesDir(List<File> result, File dir, String[] extensions) {
        List<File> files = filesMap.get(dir);
        addFiles(result, files, extensions);
        List<File> subDirs = directoryMap.get(dir);
        for (File subDir : subDirs) {
            processListFilesDir(result, subDir, extensions);
        }
    }

    private void addFiles(List<File> result, List<File> files, String[] extensions) {
        for (File f : files) {
            String fileName = f.getName();
            if (extensions != null) {
                for (String ext : extensions) {
                    if (fileName.endsWith(ext)) {
                        addFileIfNotTooBig(result, f);
                    }
                }
            } else {
                // null: accept all files
                addFileIfNotTooBig(result, f);
            }
        }
    }

    private void processDir(File f) {
        File[] files = f.listFiles();
        List<File> subDirs = new ArrayList<File>();
        List<File> dirFiles = new ArrayList<File>();

        for (File file : files) {
            if (file.isDirectory()) {
                if (shouldDirectoryBeIncluded(file)) {
                    addFileIfNotTooBig(subDirs, file);
                    String absolutePath = file.getAbsolutePath();
                    fileNameToFileMap.put(absolutePath, file);
                    fileToAbsolutePathNameMap.put(file, absolutePath);
                }
            } else {
                if (shouldFileBeIncluded(file)) {
                    addFileIfNotTooBig(dirFiles, file);
                    String absolutePath = file.getAbsolutePath() + File.separator + file.getName();
                    fileNameToFileMap.put(absolutePath, file);
                    fileToAbsolutePathNameMap.put(file, absolutePath);
                }
            }
        }

        directoryMap.put(f, subDirs);
        filesMap.put(f, dirFiles);

        for (File dir : subDirs) {
            processDir(dir);
        }
    }

    private boolean shouldFileBeIncluded(File file) {
        if (extensionsToExclude != null) {
            String fileName = file.getName();
            for (String fileNameExtensionToExclude : extensionsToExclude) {
                if (fileName.endsWith(fileNameExtensionToExclude)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean shouldDirectoryBeIncluded(File file) {
        if (directoryNamesToExclude != null) {
            String dirName = file.getName();
            for (String dirNameToExclude : directoryNamesToExclude) {
                if (dirNameToExclude.equals(dirName)) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public String getCanonicalPathForSomethingReturnedByFileListerService(File f) {
        return fileToAbsolutePathNameMap.get(f);
    }
}
