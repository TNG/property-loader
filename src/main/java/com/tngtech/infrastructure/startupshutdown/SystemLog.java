package com.tngtech.infrastructure.startupshutdown;

import com.tngtech.infrastructure.util.StackTraceUtils;

public class SystemLog {
    public static void info(String message) {
        out("INFO: " + message);
    }

    public static void debug(String message) {
        out("DEBUG: " + message);
    }

    public static void warn(String message, Throwable ex) {
        err("WARN: " + message, ex);
    }

    public static void warn(String message) {
        err("WARN: " + message);
    }

    public static void error(String message, Throwable ex) {
        err("ERROR: " + message, ex);
    }

    public static void error(String message) {
        err("ERROR: " + message);
    }

    private static void out(String message) {
        System.out.println(message);
    }

    private static void err(String message, Throwable ex) {
        err(message + StackTraceUtils.getStackTrace(ex));
    }

    private static void err(String message) {
        System.err.println(message);
    }
}
