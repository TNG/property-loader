package com.tngtech.infrastructure.util.io;

import java.io.IOException;
import java.io.InputStream;

import com.tngtech.infrastructure.exception.ProblemLocatorException;
import com.tngtech.infrastructure.exception.Reject;
import com.tngtech.infrastructure.exception.SystemException;
import org.apache.log4j.Logger;

/**
 * General utility methods for working with IO
 */
public abstract class IoUtils {
    private static final Logger LOG = Logger.getLogger(IoUtils.class);
    public static final long DEFAULT_MAX_SIZE = 1024*1024; // 1 MB
    private static final int DEFAULT_BUFFER_SIZE = 4096;

    public static byte[] readFully(InputStream inputStream, boolean closeInputStream) {
        return readFully(inputStream, closeInputStream, DEFAULT_MAX_SIZE);
    }

    public static byte[] readFully(InputStream inputStream, boolean closeInputStream, long maxSize) {
        Reject.ifNull("no stream to read", inputStream);
        try {
            byte[] data = new byte[DEFAULT_BUFFER_SIZE];

            int currentLength = 0;
            byte[] currentData = new byte[0];

            int readLength = inputStream.read(data);
            while(readLength > 0) {
                int newCurrentLength = currentLength + readLength;
                if (newCurrentLength > maxSize) {
                    String error = "max size reached while reading input stream";
                    LOG.error("readFully() - " + error, new ProblemLocatorException());
                    throw new SystemException(error);
                }
                byte[] newCurrentData = new byte[newCurrentLength];

                System.arraycopy(currentData, 0, newCurrentData, 0, currentLength);
                System.arraycopy(data, 0, newCurrentData, currentLength, readLength);

                currentData = newCurrentData;
                currentLength = newCurrentLength;

                readLength = inputStream.read(data);
            }

            return currentData;
        } catch (IOException e) {
            LOG.error("readFully() - can't fully read input stream: " + e, e);
            return null;
        } finally {
            if (closeInputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOG.warn("readFully() - can't close input stream: " + inputStream);
                }
            }
        }
    }
}
