package de.jupf.staticlog;

import de.jupf.staticlog.format.LogFormat;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 03.02.2016.
 *
 * @author J.Pfeifer
 */
public interface Logger {

    /**
     * Logs a debug message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     * @param throwable The log-related throwable
     */
    void debug(@NotNull String message, @NotNull String tag, @NotNull Throwable throwable);

    /**
     * Logs an info message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     * @param throwable The log-related throwable
     */
    void info(@NotNull String message, @NotNull String tag, @NotNull Throwable throwable);

    /**
     * Logs a warning message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     * @param throwable The log-related throwable
     */
    void warn(@NotNull String message, @NotNull String tag, @NotNull Throwable throwable);

    /**
     * Logs an error message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     * @param throwable The log-related throwable
     */
    void error(@NotNull String message, @NotNull String tag, @NotNull Throwable throwable);

    /**
     * Logs a debug message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     * @param throwable The log-related throwable
     */
    void debug(@NotNull String message, @NotNull Throwable throwable);

    /**
     * Logs a debug message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     */
    void debug(@NotNull String message, @NotNull String tag);

    /**
     * Logs a debug message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    void debug(@NotNull String message);

    /**
     * Logs an info message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     * @param throwable The log-related throwable
     */
    void info(@NotNull String message, @NotNull Throwable throwable);

    /**
     * Logs an info message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     */
    void info(@NotNull String message, @NotNull String tag);

    /**
     * Logs an info message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    void info(@NotNull String message);

    /**
     * Logs a warning message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     * @param throwable The log-related throwable
     */
    void warn(@NotNull String message, @NotNull Throwable throwable);

    /**
     * Logs a warning message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     */
    void warn(@NotNull String message, @NotNull String tag);

    /**
     * Logs a warning message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    void warn(@NotNull String message);

    /**
     * Logs an error message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     * @param throwable The log-related throwable
     */
    void error(@NotNull String message, @NotNull Throwable throwable);

    /**
     * Logs an error message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     */
    void error(@NotNull String message, @NotNull String tag);

    /**
     * Logs an error message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    void error(@NotNull String message);

    /**
     * Sets a tag filter for this Logger.
     * Only messages with this tag will be printed.
     *
     * @param filterTag every other tag then this will be filtered
     */
    void setTagFilter(@NotNull String filterTag);

    /**
     * Deletes a previously set tag filter.
     */
    void deleteTagFilter();

    /**
     * This method deletes the old LogFormat and
     * returns a handle to create the new format with.
     *
     * @return log format handle
     */
    LogFormat newFormat();
}
