package com.tngtech.infrastructure.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;

import com.tngtech.infrastructure.exception.SystemException;
import com.tngtech.infrastructure.util.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

public abstract class StackTraceUtils {

    public static String toString(Throwable ex) {
        return getStackTrace(ex);
    }

    /**
     * Returns the Stacktrace of a Throwable.
     *
     * @param throwable a Throwable
     * @return Stacktrace
     */
    public static String getStackTrace(Throwable throwable) {
        if (throwable == null) {
            return "[no throwable == no stacktrace]";
        }
        if (throwable instanceof BatchUpdateException) {
            return getStackTrace((BatchUpdateException) throwable);
        }
        if (throwable instanceof SQLException) {
            return getStackTrace((SQLException) throwable);
        }
        if (throwable instanceof InvocationTargetException) {
            return getStackTrace((InvocationTargetException) throwable);
        }
        if (throwable instanceof ServletException) {
            return getStackTrace((ServletException) throwable);
        }

        return printStackTrace(throwable);
    }

    /**
     * Returns the Stacktrace of an <code>InvocationTargetException</code>
     * That means also the Stacktrace of a potential toggled
     * Exception.
     *
     * @param exception an InvocationTargetException
     * @return Stacktrace
     */
    public static String getStackTrace(InvocationTargetException exception) {
        if (exception == null) {
            return "[no exception == no stacktrace]";
        }
        StringBuilder theTrace = new StringBuilder();
        theTrace.append(printStackTrace(exception));
        if (exception.getTargetException() != null) {
            return theTrace.append(getStackTrace(exception.getTargetException())).toString();
        }
        return theTrace.toString();
    }

    /**
     * Returns the Stacktrace of a <code>SQLException</code>
     * That means also the Stacktrace of a potential toggled
     * Exception.
     *
     * @param sqlException an SQLException
     * @return Stacktrace
     */
    public static String getStackTrace(SQLException sqlException) {
        if (sqlException == null) {
            return "[no exception == no stacktrace]";
        }
        StringBuilder theTrace = new StringBuilder();
        theTrace.append(printStackTrace(sqlException));
        if (sqlException.getNextException() != null) {
            return theTrace.append(getStackTrace(sqlException.getNextException())).toString();
        }
        return theTrace.toString();
    }

    /**
     * Returns the Stacktrace of a <code>ServletException</code>
     * That means also the Stacktrace of a potential toggled
     * Exception.
     *
     * @param servletException a ServletException
     * @return Stacktrace
     */
    public static String getStackTrace(ServletException servletException) {
        if (servletException == null) {
            return "[no exception == no stacktrace]";
        }
        StringBuilder theTrace = new StringBuilder();
        theTrace.append(printStackTrace(servletException));
        if (servletException.getRootCause() != null) {
            return theTrace.append(getStackTrace(servletException.getRootCause())).toString();
        }
        return theTrace.toString();
    }

    public static String getStackTrace(BatchUpdateException exception) {
        if (exception == null) {
            return "[no exception == no stacktrace]";
        }
        StringBuilder theTrace = new StringBuilder();
        theTrace.append(printStackTrace(exception));
        if (exception.getNextException() != null) {
            return theTrace.append(getStackTrace(exception.getNextException())).toString();
        }
        return theTrace.toString();
    }


    /**
     * Returns the Stacktrace of a <code>Throwables</code>
     * without the toggled Exception.
     *
     * @param throwable a Throwable
     * @return Stacktrace
     */
    public static String printStackTrace(Throwable throwable) {
        if (throwable != null) {
            ByteArrayOutputStream theStream = new ByteArrayOutputStream();
            printStackTrace(throwable, new PrintWriter(theStream, true));
            return theStream.toString();
        }
        return "";
    }

    public static void printStackTrace(Throwable t, PrintWriter s) {
        s.println(t);
        StackTraceElement[] trace = t.getStackTrace();
        for (StackTraceElement aTrace : trace) {
            s.println("\tat " + aTrace);
        }

        Set<Throwable> visited = new HashSet<Throwable>();
        visited.add(t);
        Throwable cause = t.getCause();
        if (cause != null && !visited.contains(cause)) {
            printStackTraceAsCause(s, cause, visited);
        }
    }

    /**
     * Print our stack trace as a cause for the specified stack trace.
     */
    private static void printStackTraceAsCause(PrintWriter s, Throwable cause, Set<Throwable> visited) {
        s.println("Caused by: " + cause);
        StackTraceElement[] trace = cause.getStackTrace();
        for (StackTraceElement aTrace : trace) {
            s.println("\tat " + aTrace);
        }

        visited.add(cause);
        Throwable ourCause = cause.getCause();
        if (ourCause != null && !visited.contains(ourCause)) {
            printStackTraceAsCause(s, ourCause, visited);
        }
        if (cause instanceof BatchUpdateException) {
            BatchUpdateException bu = (BatchUpdateException) cause;
            printStackTraceAsCause(s, bu.getNextException(), visited);
        }
    }

    public static String getCurrentStackTrace() {
        Thread currentThread = Thread.currentThread();
        StringBuilder sb = new StringBuilder().append("Current StackTrace of ").append(currentThread.toString());
        StackTraceElement[] trace = currentThread.getStackTrace();
        for (StackTraceElement aTrace : trace) {
            sb.append("\n\t").append(aTrace);
        }
        return sb.toString();
    }

    public static String getStackTrace(Thread thread) {
        StringBuilder stringBuilder = new StringBuilder();
        if (thread == null) {
            stringBuilder.append("[no thread given]");
        } else {
            stringBuilder.append("Thread=\"");
            stringBuilder.append(thread.getName());
            stringBuilder.append(" \"in threadGroup=\"");
            stringBuilder.append(thread.getThreadGroup().getName());
            stringBuilder.append("\" state=");
            stringBuilder.append(thread.getState().name());
            stringBuilder.append(" alive=");
            stringBuilder.append(thread.isAlive());
            stringBuilder.append(" deamon=");
            stringBuilder.append(thread.isDaemon());
            stringBuilder.append(" interrupted=");
            stringBuilder.append(thread.isInterrupted());
            stringBuilder.append(" id=");
            stringBuilder.append(thread.getId());
            stringBuilder.append(" priority=");
            stringBuilder.append(thread.getPriority());

            stringBuilder.append(FileUtils.LINE_SEPARATOR);
            stringBuilder.append("Stacktrace:");
            stringBuilder.append(FileUtils.LINE_SEPARATOR);

            StackTraceElement[] stackTraceElements = thread.getStackTrace();
            if (stackTraceElements != null) {
                if (stackTraceElements.length > 0) {
                    for (StackTraceElement s : stackTraceElements) {
                        stringBuilder.append("\t");
                        stringBuilder.append(s.toString());
                        stringBuilder.append(FileUtils.LINE_SEPARATOR);
                    }
                } else {
                    stringBuilder.append("\tempty stacktrace");
                }
            } else {
                stringBuilder.append("\tnot available");
            }
            stringBuilder.append(FileUtils.LINE_SEPARATOR);
        }
        return stringBuilder.toString();
    }

    /**
     * Takes a given hierarchy of Throwables and converts them to the specified type. This is
     * useful if e.g. exceptions need to be serialized to a client which does not have the
     * correct exception classes.
     *
     * @param t           the throwable to convert. If it has 'cause' exceptions, these are converted as well
     * @param targetClass the new subclass of Throwable the exceptions should be converted to. The
     *                    class must have a constructor which takes a string and a throwable.
     * @return the converted throwable
     * @throws SystemException if the conversion failed. In that case, the original exception
     *                         is added as a cause of this exception.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> T convertThrowable(Throwable t, Class<T> targetClass) {
        try {
            Constructor<T> constructor = targetClass.getConstructor(String.class, Throwable.class);
            return (T) convertThrowable(t, constructor, 0);
        } catch (NoSuchMethodException e) {
            throw new SystemException("Could not convert exception due to \"" + e.getMessage() + "\". Setting original exception as cause.", t);
        } catch (InstantiationException e) {
            throw new SystemException("Could not convert exception due to \"" + e.getMessage() + "\". Setting original exception as cause.", t);
        } catch (IllegalAccessException e) {
            throw new SystemException("Could not convert exception due to \"" + e.getMessage() + "\". Setting original exception as cause.", t);
        } catch (InvocationTargetException e) {
            throw new SystemException("Could not convert exception due to \"" + e.getMessage() + "\". Setting original exception as cause.", t);
        }
    }

    private static final int MAX_EXCEPTION_DEPTH = 10;
    private static final String CONVERSION_HINT = " [converted from %s]";

    private static Throwable convertThrowable(Throwable source, Constructor<?> constructor, int currentDepth) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        if (currentDepth >= MAX_EXCEPTION_DEPTH) {
            return null;
        }
        Throwable causeCopy = null;
        Throwable cause = ExceptionUtils.getCause(source);
        if (cause != null) {
            causeCopy = convertThrowable(cause, constructor, currentDepth + 1);
        }
        String message = source.getMessage() + String.format(CONVERSION_HINT, source.getClass().getName());
        Throwable result = (Throwable) constructor.newInstance(message, causeCopy);
        result.setStackTrace(source.getStackTrace());
        return result;
    }
}
