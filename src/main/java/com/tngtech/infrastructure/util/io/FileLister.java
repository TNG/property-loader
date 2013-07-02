package com.tngtech.infrastructure.util.io;

import java.io.File;
import java.util.List;

/**
 * List files from a file system
 */
public interface FileLister {
    /**
     * List files with given extensions recursivly from a given starting point in a file system,
     * @param startingPoint the starting point for the search. Has to be a directory
     * @param extensions the extensions to search. Has to be provided.
     * @return a list of files below the given staring point with the necessary extensions.
     */
    List<File> listFiles(File startingPoint, String[] extensions);

    /**
     * List files with given extensions recursivly from a given starting point in a file system,
     * @param startingPoint the starting points for the search. Have to be directories
     * @param extensions the extensions to search. Has to be provided.
     * @return a list of files below the given staring point with the necessary extensions.
     */
    List<File> listFiles(File[] startingPoint, String[] extensions);

    /**
     * Return the canonical path for a file that was returned by the FileLister.
     * Should be used instead of File#getCanonicalPath() for performance reasons.
     * @param f the file to get the canonical path for
     * @return the canonical path as string
     */
    String getCanonicalPathForSomethingReturnedByFileListerService(File f);

    /**
     * Return all files (except directories) known to the file lister
     * @param startingPoint the starting point
     * @return a list of all files (except directories) below the given staring point
     */
    List<File> listAllFiles(File startingPoint);
}
