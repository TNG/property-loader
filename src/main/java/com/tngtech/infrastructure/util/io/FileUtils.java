package com.tngtech.infrastructure.util.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.tngtech.infrastructure.exception.Exceptions;
import com.tngtech.infrastructure.exception.ProblemLocatorException;
import com.tngtech.infrastructure.exception.Reject;
import com.tngtech.infrastructure.exception.SystemException;
import com.tngtech.infrastructure.util.LocalizationConstants;
import org.apache.log4j.Logger;

public class FileUtils {
    private static final Logger LOG = Logger.getLogger(FileUtils.class);

    /**
     * Prefer {@link #DEFAULT_CHARSET} when possible, because e.g. java.io will not throw checked exceptions for {@link java.nio.charset.Charset} usage!
     */
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_ENCODING);

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final int KILO = 1024;
    private static final char[] ILLEGAL_CHARACTERS = " /\n\r\t\0\f`?*\\<>|\":".toCharArray();

    public static String getResourceAsString(Class context, String name) {
        return getResourceAsString(context, name, DEFAULT_ENCODING);
    }

    private static String getResourceAsString(Class context, String name, String encoding) {
        try {
            File file = new File(context.getResource(name).getPath());
            return org.apache.commons.io.FileUtils.readFileToString(file, encoding);
        } catch (IOException e) {
            throw Exceptions.wrap(e);
        }
    }
    
    public static String[] getFileContentAsStrings(File file) {
        return getFileContentAsStrings(file, DEFAULT_ENCODING);
    }
    
    private static String[] getFileContentAsStrings(File file, String encoding) {
        List<String> result = new ArrayList<String>();

        if (isFile(file)) {
            FileInputStream fileInputStream   = null;
            BufferedReader reader             = null;
            try {
                fileInputStream   = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, encoding);
                reader            = new BufferedReader(inputStreamReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    result.add(line);
                }
            } catch (IOException e) {
                String error = "Exception while reading file: " + file + ": " + e;
                LOG.error("getFileContentAsStrings() - " + error);
                throw new SystemException(error, e);
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException t) {
                    LOG.error("getFileContentAsStrings() - can't close reader for file " + file + ": " + t, t);
                    // try to close the file if all else fails
                    try {
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                    } catch (IOException t2) {
                        LOG.error("getFileContentAsStrings() - can't close file reader for file " + file + ": " + t2, t2);
                    }
                }
            }
        } else {
            LOG.error("getFileContentAsStrings() - file " + file + " is not an existing file!");
        }
        return result.toArray(new String[result.size()]);
    }

    public static boolean isFile(File file) {
        if (file == null) {
            LOG.debug("isFile() - parameter is null", new ProblemLocatorException());
            return false;
        }
        if (!file.exists()) {
            LOG.debug("isFile() - file '" + file + "' does not exist", new ProblemLocatorException());
            return false;
        }
        if (!file.isFile()) {
            LOG.debug("isFile() - file '" + file + "' is not a physical file", new ProblemLocatorException());
            return false;
        }
        return true;
    }

    public static boolean isDirectory(File dir) {
        if (dir == null) {
            LOG.debug("isDirectory() - parameter is null", new ProblemLocatorException());
            return false;
        }
        if (!dir.exists()) {
            LOG.debug("isDirectory() - file '" + dir + "' does not exist", new ProblemLocatorException());
            return false;
        }
        if (!dir.isDirectory()) {
            LOG.debug("isDirectory() - file '" + dir + "' is not a directory", new ProblemLocatorException());
            return false;
        }
        return true;
    }



    private static final String[] UNITS = new String[] {"Bytes", "kB", "MB", "GB", "TB", "PB"};

    /**
     * Returns the corresponding representation of a long value
     * in the <code>kB</code>, <code>MB</code>,... writing
     * @param size if larger than PB the value in PB is returned
     * @return formatted String.
     */
    public static String formatSize(long size) {
        boolean negative = false;
        if (size < 0) {
            negative = true;
            size = -size;
        }

        double value = size;
        int count = 0;
        int factor = KILO;
        while (value > 100) {
            if (count == UNITS.length - 1) {
                break;
            }
            value /= factor;
            ++count;
        }
        StringBuilder result = new StringBuilder();
        NumberFormat nf = NumberFormat.getNumberInstance(LocalizationConstants.DEFAULT_LOCALE);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(0);
        nf.setGroupingUsed(true);
        if (negative) {
            result.append("-");
        }
        result.append(nf.format(value))
              .append(' ')
              .append(UNITS[count]);
        return result.toString();
    }

    public static File[] getAllSubDirs(File root, boolean includeRoot) {
        List<File> files = new ArrayList<File>();
        addSubDirs(files, root, includeRoot);
        return files.toArray(new File[files.size()]);
    }

    private static void addSubDirs(List<File> files, File root, boolean includeRoot) {
        if (includeRoot) {
            files.add(root);
        }

        File[] subdirs = root.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        for (File subdir : subdirs) {
            addSubDirs(files, subdir, true);
        }
    }

    /**
     * Returns the path portion of an URL, unescaped.
     *
     * Equivalent to url.getPath(), except that URL escaping is reversed, e.g. %20 -> ' '
     */
    public static String pathFromUrl(URL url) {
        try {
            return url.toURI().getPath();
        } catch (URISyntaxException e) {
            throw new SystemException(e);
        }
    }

    public static String normalizePath(String path) {
        String separator = File.separator;

        if ("\\".equals(separator)) {
            return path.replace("/", separator);
        }
        if ("/".equals(separator)) {
            return path.replace("\\", separator);
        }

        return path;
    }

    public static String sanitizeAsFileName(String s) {
        Reject.ifNull(s);
        String result = s;

        for (char illegal : ILLEGAL_CHARACTERS) {
            result = result.replace(illegal, '-');
        }

        return result;
    }

    public static void mkdir(String dir) {
        boolean success = new File(dir).mkdir();

        if (!success) {
            LOG.debug("mkdir() - failed to create directory " + dir);
        }
    }
}
