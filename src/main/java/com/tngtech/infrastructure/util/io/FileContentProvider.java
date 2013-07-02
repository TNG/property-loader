package com.tngtech.infrastructure.util.io;

import java.io.File;

/**
 * Get the content of files.
 */
public interface FileContentProvider {
    /**
     * Get the file content for a given file.
     * @param f the file to get the content for
     * @return a byte array containing the file content
     */
    byte[] getFileContent(File f);

    /**
     * Get the file content for a given file as string.
     * @param f the file to get the content for
     * @param encoding the encoding to expect
     * @return the file content as string
     */
    String getFileContent(File f, String encoding);

    /**
     * If the implementation does some sort of caching, update the cache for the given file
     * @param f the file to update the cache for
     * @param encoding the encoding of the file (may be null if only getFileContent(File f) was called before)
     */
    void removeFromCache(File f, String encoding);
}
