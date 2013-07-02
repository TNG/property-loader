package com.tngtech.infrastructure.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.tngtech.infrastructure.exception.Reject;
import com.tngtech.infrastructure.exception.SystemException;
import org.apache.log4j.Logger;

/**
 * Default implementation of the FileContentProvider interface.
 * Just returns the file content.
 */
public class DefaultFileContentProvider implements FileContentProvider {
    private static final Logger LOG = Logger.getLogger(DefaultFileContentProvider.class);

    @Override
    public byte[] getFileContent(File f) {
        Reject.ifNull("no file provided", f);
        int length = (int) f.length();
        byte[] data = new byte[length];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(f);
            int readLength = fileInputStream.read(data);
            if (readLength != length) {
                throw new SystemException("read length and file length differ for file " + f.getAbsolutePath());
            }
        } catch (IOException e) {
            throw new SystemException("error while reading file " + f.getAbsolutePath() + ": " + e, e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException ioe) {
                    LOG.warn("getFileContent() - error while closing input stream for file " + f.getAbsolutePath(), ioe);
                }
            }
        }
        return data;
    }

    @Override
    public String getFileContent(File f, String encoding) {
        Reject.ifNull("no file provided", f);
        Reject.ifNull("no encoding provided", encoding);

        byte[] data = getFileContent(f);
        if (data == null) {
            return null;
        }

        if (encoding.equals("UTF-8") && data.length > 3 && data[0] == -17 && data[1] == -69 && data[2] == -65) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("getFileContent() - recognized UTF-8-BOM for file " + f + ", removing it");
            }
            try {
                return new String(data, 3, data.length - 3, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new IllegalArgumentException("should not happen: UTF-8 not supported!?: " + e, e);
            }
        }
        try {
            return new String(data, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new SystemException("unknown encoding: " + e, e);
        }
    }

    @Override
    public void removeFromCache(File f, String encoding) {
        // nothing to do
    }
}
