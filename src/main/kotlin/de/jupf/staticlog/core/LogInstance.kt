package de.jupf.staticlog.core

import de.jupf.staticlog.Logger
import de.jupf.staticlog.format.LogFormat
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write


/**
 * @author J.Pfeifer
 * @created 19.01.2016
 */
open class LogInstance() : Logger {

    protected val lock = ReentrantReadWriteLock()

    val logFormat = LogFormat() // The defined log format for this logger
    open var logLevel = LogLevel.DEBUG // Defines the minimum log level to print
        set(value) = lock.write { field = value }
    var filterTag: String
        get() = logFormat.filterTag
        set(value) {
            logFormat.filterTag = value
        }

    constructor(needLongTrace: Int) : this() {
        logFormat.traceSteps = needLongTrace
    }

    /**
     * Logs a debug message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     * @param exception The log-related exception
     */
    override fun debug(message: String, tag: String, exception: Exception) {
        if (!checkLogLevel(LogLevel.DEBUG)) return
        debug(message, tag, exception, logFormat)
    }

    /**
     * Logs a debug message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     * @param exception The log-related exception
     */
    override fun debug(message: String, exception: Exception) {
        if (!checkLogLevel(LogLevel.DEBUG)) return
        debug(message, "", exception, logFormat)
    }

    /**
     * Logs a debug message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     */
    override fun debug(message: String, tag: String) {
        if (!checkLogLevel(LogLevel.DEBUG)) return
        debug(message, tag, null, logFormat)
    }

    /**
     * Logs a debug message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    override fun debug(message: String) {
        if (!checkLogLevel(LogLevel.DEBUG)) return
        debug(message, "", null, logFormat)
    }

    /**
     * Logs an info message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     * @param exception The log-related exception
     */
    override fun info(message: String, tag: String, exception: Exception) {
        if (!checkLogLevel(LogLevel.INFO)) return;
        info(message, tag, exception, logFormat)
    }

    /**
     * Logs an info message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     * @param exception The log-related exception
     */
    override fun info(message: String, exception: Exception) {
        if (!checkLogLevel(LogLevel.INFO)) return;
        info(message, "", exception, logFormat)
    }

    /**
     * Logs an info message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     */
    override fun info(message: String, tag: String) {
        if (!checkLogLevel(LogLevel.INFO)) return;
        info(message, tag, null, logFormat)
    }

    /**
     * Logs an info message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    override fun info(message: String) {
        if (!checkLogLevel(LogLevel.INFO)) return;
        info(message, "", null, logFormat)
    }

    /**
     * Logs a warning message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     * @param exception The log-related exception
     */
    override fun warn(message: String, tag: String, exception: Exception) {
        if (!checkLogLevel(LogLevel.WARN)) return
        warn(message, tag, exception, logFormat)
    }

    /**
     * Logs a warning message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     * @param exception The log-related exception
     */
    override fun warn(message: String, exception: Exception) {
        if (!checkLogLevel(LogLevel.WARN)) return
        warn(message, "", exception, logFormat)
    }

    /**
     * Logs a warning message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     */
    override fun warn(message: String, tag: String) {
        if (!checkLogLevel(LogLevel.WARN)) return
        warn(message, tag, null, logFormat)
    }

    /**
     * Logs a warning message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    override fun warn(message: String) {
        if (!checkLogLevel(LogLevel.WARN)) return
        warn(message, "", null, logFormat)
    }

    /**
     * Logs an error message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     * @param exception The log-related exception
     */
    override fun error(message: String, tag: String, exception: Exception) {
        error(message, tag, exception, logFormat)
    }

    /**
     * Logs an error message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     * @param exception The log-related exception
     */
    override fun error(message: String, exception: Exception) {
        error(message, "", exception, logFormat)
    }

    /**
     * Logs an error message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     */
    override fun error(message: String, tag: String) {
        error(message, tag, null, logFormat)
    }

    /**
     * Logs an error message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    override fun error(message: String) {
        error(message, "", null, logFormat)
    }

    /**
     * Sets a tag filter for this Logger.
     * Only messages with this tag will be printed.
     *
     * @param filterTag
     */
    override fun setTagFilter(filterTag: String) {
        logFormat.filterTag = filterTag
        logFormat.tagFilterUsed = true
    }

    /**
     * Deletes a previously set tag filter.
     */
    override fun deleteTagFilter() {
        logFormat.tagFilterUsed = false
    }

    /**
     * This method deletes the old [LogFormat] and
     * returns a handle to create the new format with.
     *
     * @return log format handle
     */
    override fun newFormat(): LogFormat {
        logFormat.children.clear()
        logFormat.occurrenceUsed = false
        logFormat.tagUsed = false
        return logFormat
    }

    /**
     * This function deletes the old LogFormat.
     * Then executes the given [build] function to create a new log format.
     */
    fun newFormat(build: LogFormat.() -> Unit) {
        logFormat.build(build)
    }

    /**
     * Checks the given [level] against the LogLevel set in the Logger-instance.
     */
    private fun checkLogLevel(level: LogLevel) = lock.read { logLevel <= level }
}