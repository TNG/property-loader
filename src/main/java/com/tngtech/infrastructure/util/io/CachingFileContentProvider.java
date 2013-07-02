package com.tngtech.infrastructure.util.io;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachingFileContentProvider implements FileContentProvider {
    private FileContentProvider fileContentProvider;
    private Map<File, byte[]> byteContentCache = new ConcurrentHashMap<File, byte[]>();
    private Map<CacheKeyEntry, String> stringContentCache = new ConcurrentHashMap<CacheKeyEntry, String>();

    public CachingFileContentProvider(FileContentProvider fileContentProvider) {
        this.fileContentProvider = fileContentProvider;
    }

    @Override
    public byte[] getFileContent(File f) {
        byte[] result = byteContentCache.get(f);
        if (result == null) {
            result = fileContentProvider.getFileContent(f);
            byteContentCache.put(f, result);
        }
        return result;
    }

    @Override
    public String getFileContent(File f, String encoding) {
        CacheKeyEntry key = new CacheKeyEntry(f, encoding);
        String result = stringContentCache.get(key);
        if (result == null) {
            result = fileContentProvider.getFileContent(f, encoding);
            stringContentCache.put(key, result);
        }
        return result;
    }

    @Override
    public void removeFromCache(File f, String encoding) {
        if (encoding != null) {
            CacheKeyEntry key = new CacheKeyEntry(f, encoding);
            stringContentCache.remove(key);
        }
        byteContentCache.remove(f);
    }

    private static class CacheKeyEntry {
        private int hashCode;
        private String equalsKey;

        public CacheKeyEntry(File f, String encoding) {
            this.equalsKey = f.getAbsolutePath()+"::"+encoding;
            this.hashCode = equalsKey.hashCode();
        }

        @Override
        public int hashCode() {
            return hashCode;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (obj instanceof CacheKeyEntry) {
                CacheKeyEntry other = (CacheKeyEntry) obj;
                return other.equalsKey.equals(equalsKey);
            }
            return false;
        }
    }
}
