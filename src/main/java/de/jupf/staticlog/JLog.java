package de.jupf.staticlog;

import de.jupf.staticlog.format.LogFormat;
import de.jupf.staticlog.core.LogInstance;
import de.jupf.staticlog.core.LogLevel;
import de.jupf.staticlog.format.Builder;
import de.jupf.staticlog.format.Indent;
import de.jupf.staticlog.format.Line;
import org.jetbrains.annotations.NotNull;

/**
 * The StaticLog Java logging interface class
 *
 * @author J.Pfeifer
 * created on 03.02.2016.
 */
public class JLog {
    protected static LogInstance logInstance = Log.INSTANCE.getLogInstance();

    /**
     * Returns a logger instance for Java
     */
    public static Logger javaInstance() {
        return new LogInstance(2);
    }

    /**
     * Returns a logger instance for kotlin
     */
    public static LogInstance kotlinInstance() {
        return new LogInstance();
    }

    /**
     * Logs a debug message.
     *
     * @param message   The log message
     * @param tag       The tag the message is logged under
     * @param exception The log-related exception
     */
    public static void debug(@NotNull String message, @NotNull String tag, @NotNull Exception exception) {
        logInstance.debug(message, tag, exception);
    }

    /**
     * Logs a debug message.
     * The tag will default to the class name the log is created from.
     *
     * @param message   The log message
     * @param exception The log-related exception
     */
    public static void debug(@NotNull String message, @NotNull Exception exception) {
        logInstance.debug(message, exception);
    }

    /**
     * Logs a debug message.
     *
     * @param message The log message
     * @param tag     The tag the message is logged under
     */
    public static void debug(@NotNull String message, @NotNull String tag) {
        logInstance.debug(message, tag);
    }

    /**
     * Logs a debug message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    public static void debug(@NotNull String message) {
        logInstance.debug(message);
    }

    /**
     * Logs an info message.
     *
     * @param message   The log message
     * @param tag       The tag the message is logged under
     * @param exception The log-related exception
     */
    public static void info(@NotNull String message, @NotNull String tag, @NotNull Exception exception) {
        logInstance.info(message, tag, exception);
    }

    /**
     * Logs an info message.
     * The tag will default to the class name the log is created from.
     *
     * @param message   The log message
     * @param exception The log-related exception
     */
    public static void info(@NotNull String message, @NotNull Exception exception) {
        logInstance.info(message, exception);
    }

    /**
     * Logs an info message.
     *
     * @param message The log message
     * @param tag     The tag the message is logged under
     */
    public static void info(@NotNull String message, @NotNull String tag) {
        logInstance.info(message, tag);
    }

    /**
     * Logs an info message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    public static void info(@NotNull String message) {
        logInstance.info(message);
    }

    /**
     * Logs a warning message.
     *
     * @param message   The log message
     * @param tag       The tag the message is logged under
     * @param exception The log-related exception
     */
    public static void warn(@NotNull String message, @NotNull String tag, @NotNull Exception exception) {
        logInstance.warn(message, tag, exception);
    }

    /**
     * Logs a warning message.
     * The tag will default to the class name the log is created from.
     *
     * @param message   The log message
     * @param exception The log-related exception
     */
    public static void warn(@NotNull String message, @NotNull Exception exception) {
        logInstance.warn(message, exception);
    }

    /**
     * Logs a warning message.
     *
     * @param message The log message
     * @param tag     The tag the message is logged under
     */
    public static void warn(@NotNull String message, @NotNull String tag) {
        logInstance.warn(message, tag);
    }

    /**
     * Logs a warning message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    public static void warn(@NotNull String message) {
        logInstance.warn(message);
    }


    /**
     * Logs an error message.
     *
     * @param message   The log message
     * @param tag       The tag the message is logged under
     * @param exception The log-related exception
     */
    public static void error(@NotNull String message, @NotNull String tag, @NotNull Exception exception) {
        logInstance.error(message, tag, exception);
    }

    /**
     * Logs an error message.
     * The tag will default to the class name the log is created from.
     *
     * @param message   The log message
     * @param exception The log-related exception
     */
    public static void error(@NotNull String message, Exception exception) {
        logInstance.error(message, exception);
    }

    /**
     * Logs an error message.
     *
     * @param message The log message
     * @param tag     The tag the message is logged under
     */
    public static void error(@NotNull String message, @NotNull String tag) {
        logInstance.error(message, tag);
    }

    /**
     * Logs an error message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    public static void error(@NotNull String message) {
        logInstance.error(message);
    }

    public static void setLogLevel(LogLevel logLevel) {
        logInstance.setLogLevel(logLevel);
    }

    public static LogLevel getLogLevel() {
        return logInstance.getLogLevel();
    }

    /**
     * This method deletes the old [LogFormat] and
     * returns a handle to create the new format with.
     *
     * @return log format handle
     */
    public static LogFormat newFormat() {
        return logInstance.newFormat();
    }

    public static LogFormat getFormat() {
        return logInstance.getLogFormat();
    }

    public static class FormatOperations {

        /**
         * Creates a space [Builder] which prints [times] spaces.
         *
         * @param times number of spaces to print
         * @return [Builder] for spaces
         */
        public static Builder space(Integer times) {
            return logInstance.getLogFormat().space(times);
        }

        /**
         * Creates a tab [Builder] which prints [times] tabs.
         *
         * @param times number of tabs to print
         * @return [Builder] for tabs
         */
        public static Builder tab(Integer times) {
            return logInstance.getLogFormat().tab(times);
        }

        /**
         * Creates a date [Builder] which prints the actual date/time in the given [format].
         *
         * @param format String in a [SimpleDateFormat](http://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html)
         * @return [Builder] for spaces
         */
        public static Builder date(String format) {
            return logInstance.getLogFormat().date(format);
        }

        /**
         * Creates a [Builder] which prints the current time in milliseconds.
         *
         * @return [Builder] for time since epoch
         */
        public static Builder epoch() {
            return logInstance.getLogFormat().getEpoch();
        }

        /**
         * Creates a [Builder] which prints the [LogLevel] of the log
         *
         * @return [Builder] for current [LogLevel]
         */
        public static Builder level() {
            return logInstance.getLogFormat().getLevel();
        }

        /**
         * Creates a [Builder] which prints the log message itself
         *
         * @return [Builder] for the message
         */
        public static Builder message() {
            return logInstance.getLogFormat().getMessage();
        }

        /**
         * Creates a [Builder] which prints the tag of the log
         *
         * @return [Builder] for the tag
         */
        public static Builder tag() {
            return logInstance.getLogFormat().getTag();
        }

        /**
         * Creates a [Builder] which prints the fully qualified name of the occurrence of the log
         *
         * @return [Builder] for the occurrence
         */
        public static Builder occurrence() {
            return logInstance.getLogFormat().getOccurrence();
        }

        /**
         * Creates a Builder which prints the given [text]
         *
         * @param text the text to print
         * @return [Builder] for [text]
         */
        public static Builder text(String text) {
            return logInstance.getLogFormat().text(text);
        }

        /**
         * Creates a [Builder] which prints all added Builders.
         *
         * @return [Builder] for current [LogLevel]
         */
        public static Builder line(Builder... builders) {
            Line line = new Line();
            for (Builder builder : builders) {
                line.getChildren().add(builder);
            }
            return line;
        }

        public static Builder indent(Builder... builders) {
            Indent indent = new Indent();
            for (Builder builder : builders) {
                indent.getChildren().add(builder);
            }
            return indent;
        }
    }
}
