package de.jupf.staticlog

import de.jupf.staticlog.core.LogLevel
import de.jupf.staticlog.format.LogFormat

/**
 * Created on 03.02.2016.

 * @author J.Pfeifer
 */
interface Logger {

    /**
     * Defines the current logging level.
     */
    var logLevel: LogLevel

    /**
     * Logs a debug message.

     * @param message The log message
     * *
     * @param tag The tag the message is logged under
     * *
     * @param throwable The log-related throwable
     */
    fun debug(message: String, tag: String, throwable: Throwable)

    /**
     * Logs an info message.

     * @param message The log message
     * *
     * @param tag The tag the message is logged under
     * *
     * @param throwable The log-related throwable
     */
    fun info(message: String, tag: String, throwable: Throwable)

    /**
     * Logs a warning message.

     * @param message The log message
     * *
     * @param tag The tag the message is logged under
     * *
     * @param throwable The log-related throwable
     */
    fun warn(message: String, tag: String, throwable: Throwable)

    /**
     * Logs an error message.

     * @param message The log message
     * *
     * @param tag The tag the message is logged under
     * *
     * @param throwable The log-related throwable
     */
    fun error(message: String, tag: String, throwable: Throwable)

    /**
     * Logs a debug message.
     * The tag will default to the class name the log is created from.

     * @param message The log message
     * *
     * @param throwable The log-related throwable
     */
    fun debug(message: String, throwable: Throwable)

    /**
     * Logs a debug message.

     * @param message The log message
     * *
     * @param tag The tag the message is logged under
     */
    fun debug(message: String, tag: String)

    /**
     * Logs a debug message.
     * The tag will default to the class name the log is created from.

     * @param message The log message
     */
    fun debug(message: String)

    /**
     * Logs an info message.
     * The tag will default to the class name the log is created from.

     * @param message The log message
     * *
     * @param throwable The log-related throwable
     */
    fun info(message: String, throwable: Throwable)

    /**
     * Logs an info message.

     * @param message The log message
     * *
     * @param tag The tag the message is logged under
     */
    fun info(message: String, tag: String)

    /**
     * Logs an info message.
     * The tag will default to the class name the log is created from.

     * @param message The log message
     */
    fun info(message: String)

    /**
     * Logs a warning message.
     * The tag will default to the class name the log is created from.

     * @param message The log message
     * *
     * @param throwable The log-related throwable
     */
    fun warn(message: String, throwable: Throwable)

    /**
     * Logs a warning message.

     * @param message The log message
     * *
     * @param tag The tag the message is logged under
     */
    fun warn(message: String, tag: String)

    /**
     * Logs a warning message.
     * The tag will default to the class name the log is created from.

     * @param message The log message
     */
    fun warn(message: String)

    /**
     * Logs an error message.
     * The tag will default to the class name the log is created from.

     * @param message The log message
     * *
     * @param throwable The log-related throwable
     */
    fun error(message: String, throwable: Throwable)

    /**
     * Logs an error message.

     * @param message The log message
     * *
     * @param tag The tag the message is logged under
     */
    fun error(message: String, tag: String)

    /**
     * Logs an error message.
     * The tag will default to the class name the log is created from.

     * @param message The log message
     */
    fun error(message: String)

    /**
     * Sets a tag filter for this Logger.
     * Only messages with this tag will be printed.

     * @param filterTag every other tag then this will be filtered
     */
    fun setTagFilter(filterTag: String)

    /**
     * Deletes a previously set tag filter.
     */
    fun deleteTagFilter()

    /**
     * This method deletes the old LogFormat and
     * returns a handle to create the new format with.

     * @return log format handle
     */
    fun newFormat(): LogFormat
}
