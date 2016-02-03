package de.jupf.staticlog.core

import de.jupf.staticlog.Logger
import de.jupf.staticlog.format.LogFormat
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write


/**
 * @created 19.01.2016
 * @author J.Pfeifer
 */
open class LogInstance() : Logger {

    protected val lock = ReentrantReadWriteLock()

    val logFormat = LogFormat()
    open var logLevel = LogLevel.DEBUG
        set(value) = lock.write { field = value }

    constructor(needLongTrace: Boolean) : this() {
        logFormat.longTrace = needLongTrace
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
        val trace = LogCore.getTrace(logFormat)
        LogCore.debug(message, tag, exception, trace, logFormat)
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
        val trace = LogCore.getTrace(logFormat)
        LogCore.debug(message, "", exception, trace, logFormat)
    }

    /**
     * Logs a debug message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     */
    override fun debug(message: String, tag: String) {
        if (!checkLogLevel(LogLevel.DEBUG)) return
        val trace = LogCore.getTrace(logFormat)
        LogCore.debug(message, tag, null, trace, logFormat)
    }

    /**
     * Logs a debug message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    override fun debug(message: String) {
        if (!checkLogLevel(LogLevel.DEBUG)) return
        val trace = LogCore.getTrace(logFormat)
        LogCore.debug(message, "", null, trace, logFormat)
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
        val trace = LogCore.getTrace(logFormat)
        LogCore.info(message, tag, exception, trace, logFormat)
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
        val trace = LogCore.getTrace(logFormat)
        LogCore.info(message, "", exception, trace, logFormat)
    }

    /**
     * Logs an info message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     */
    override fun info(message: String, tag: String) {
        if (!checkLogLevel(LogLevel.INFO)) return;
        val trace = LogCore.getTrace(logFormat)
        LogCore.info(message, tag, null, trace, logFormat)
    }

    /**
     * Logs an info message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    override fun info(message: String) {
        if (!checkLogLevel(LogLevel.INFO)) return;
        val trace = LogCore.getTrace(logFormat)
        LogCore.info(message, "", null, trace, logFormat)
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
        val trace = LogCore.getTrace(logFormat)
        LogCore.warn(message, tag, exception, trace, logFormat)
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
        val trace = LogCore.getTrace(logFormat)
        LogCore.warn(message, "", exception, trace, logFormat)
    }

    /**
     * Logs a warning message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     */
    override fun warn(message: String, tag: String) {
        if (!checkLogLevel(LogLevel.WARN)) return
        val trace = LogCore.getTrace(logFormat)
        LogCore.warn(message, tag, null, trace, logFormat)
    }

    /**
     * Logs a warning message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    override fun warn(message: String) {
        if (!checkLogLevel(LogLevel.WARN)) return
        val trace = LogCore.getTrace(logFormat)
        LogCore.warn(message, "", null, trace, logFormat)
    }

    /**
     * Logs an error message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     * @param exception The log-related exception
     */
    override fun error(message: String, tag: String, exception: Exception) {
        val trace = LogCore.getTrace(logFormat)
        LogCore.error(message, tag, exception, trace, logFormat)
    }

    /**
     * Logs an error message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     * @param exception The log-related exception
     */
    override fun error(message: String, exception: Exception) {
        val trace = LogCore.getTrace(logFormat)
        LogCore.error(message, "", exception, trace, logFormat)
    }

    /**
     * Logs an error message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     */
    override fun error(message: String, tag: String) {
        val trace = LogCore.getTrace(logFormat)
        LogCore.error(message, tag, null, trace, logFormat)
    }

    /**
     * Logs an error message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    override fun error(message: String) {
        val trace = LogCore.getTrace(logFormat)
        LogCore.error(message, "", null, trace, logFormat)
    }

    /**
     * This function deletes the old LogFormat.
     * Then executes the given [build] function to create a new log format.
     */
    fun format(build: LogFormat.() -> Unit) {
        logFormat.children.clear()
        logFormat.build()
    }

    /**
     * This method deletes the old [LogFormat] and
     * returns a handle to create the new format with.
     *
     * @return log format handle
     */
    override fun newFormat(): LogFormat {
        logFormat.children.clear()
        return logFormat
    }

    /**
     * Checks the given [level] against the LogLevel set in the Logger-instance.
     */
    private fun checkLogLevel(level: LogLevel) = lock.read { logLevel <= level }
}