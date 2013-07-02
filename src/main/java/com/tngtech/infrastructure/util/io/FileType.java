package com.tngtech.infrastructure.util.io;

import com.tngtech.infrastructure.exception.Reject;

/**
 * Class associating file extensions with its content types.
 */
public enum FileType {
    JPG("image/jpeg"), JPEG("image/jpeg"), GIF("image/gif"), PNG("image/png"), JS("application/x-javascript"), HTML("text/html"), CSS("text/css");

    private String contentType;

    private FileType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public static String contentTypeForExtension(String filename) {
        Reject.ifBlank(filename);

        int dotPosition = filename.lastIndexOf('.');
        Reject.ifTrue("no extension found", dotPosition == -1);

        try {
            String extension = filename.substring(dotPosition + 1).toUpperCase();
            return FileType.valueOf(extension).contentType;
        } catch (IllegalArgumentException e) {
            return "content/unknown";
        }
    }
}
