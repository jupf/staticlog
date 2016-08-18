package de.jupf.staticlog.core

import de.jupf.staticlog.Logger
import de.jupf.staticlog.format.LogFormat
import java.io.File


/**
 * @author J.Pfeifer
 * @created 19.01.2016
 */
class LogInstance() : Logger {
    val logFormat = LogFormat() // The defined log format for this logger
    override var logLevel = LogLevel.DEBUG // Defines the minimum log level to print
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
     * @param throwable The log-related throwable
     */
    override fun debug(message: String, tag: String, throwable: Throwable) {
        if (!checkLogLevel(LogLevel.DEBUG)) return
        print(LogLevel.DEBUG, message, tag, throwable)
    }

    /**
     * Logs a debug message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     * @param throwable The log-related throwable
     */
    override fun debug(message: String, throwable: Throwable) {
        if (!checkLogLevel(LogLevel.DEBUG)) return
        print(LogLevel.DEBUG, message, "", throwable)
    }

    /**
     * Logs a debug message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     */
    override fun debug(message: String, tag: String) {
        if (!checkLogLevel(LogLevel.DEBUG)) return
        print(LogLevel.DEBUG, message, tag, null)
    }

    /**
     * Logs a debug message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    override fun debug(message: String) {
        if (!checkLogLevel(LogLevel.DEBUG)) return
        print(LogLevel.DEBUG, message, "", null)
    }

    /**
     * Logs an info message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     * @param throwable The log-related throwable
     */
    override fun info(message: String, tag: String, throwable: Throwable) {
        if (!checkLogLevel(LogLevel.INFO)) return;
        print(LogLevel.INFO, message, tag, throwable)
    }

    /**
     * Logs an info message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     * @param throwable The log-related throwable
     */
    override fun info(message: String, throwable: Throwable) {
        if (!checkLogLevel(LogLevel.INFO)) return;
        print(LogLevel.INFO, message, "", throwable)
    }

    /**
     * Logs an info message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     */
    override fun info(message: String, tag: String) {
        if (!checkLogLevel(LogLevel.INFO)) return;
        print(LogLevel.INFO, message, tag, null)
    }

    /**
     * Logs an info message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    override fun info(message: String) {
        if (!checkLogLevel(LogLevel.INFO)) return;
        print(LogLevel.INFO, message, "", null)
    }

    /**
     * Logs a warning message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     * @param throwable The log-related throwable
     */
    override fun warn(message: String, tag: String, throwable: Throwable) {
        if (!checkLogLevel(LogLevel.WARN)) return
        print(LogLevel.WARN, message, tag, throwable)
    }

    /**
     * Logs a warning message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     * @param throwable The log-related throwable
     */
    override fun warn(message: String, throwable: Throwable) {
        if (!checkLogLevel(LogLevel.WARN)) return
        print(LogLevel.WARN, message, "", throwable)
    }

    /**
     * Logs a warning message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     */
    override fun warn(message: String, tag: String) {
        if (!checkLogLevel(LogLevel.WARN)) return
        print(LogLevel.WARN, message, tag, null)
    }

    /**
     * Logs a warning message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    override fun warn(message: String) {
        if (!checkLogLevel(LogLevel.WARN)) return
        print(LogLevel.WARN, message, "", null)
    }

    /**
     * Logs an error message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     * @param throwable The log-related throwable
     */
    override fun error(message: String, tag: String, throwable: Throwable) {
        print(LogLevel.ERROR, message, tag, throwable)
    }

    /**
     * Logs an error message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     * @param throwable The log-related throwable
     */
    override fun error(message: String, throwable: Throwable) {
        print(LogLevel.ERROR, message, "", throwable)
    }

    /**
     * Logs an error message.
     *
     * @param message The log message
     * @param tag The tag the message is logged under
     */
    override fun error(message: String, tag: String) {
        print(LogLevel.ERROR, message, tag, null)
    }

    /**
     * Logs an error message.
     * The tag will default to the class name the log is created from.
     *
     * @param message The log message
     */
    override fun error(message: String) {
        print(LogLevel.ERROR, message, "", null)
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
    private fun checkLogLevel(level: LogLevel) = logLevel <= level

    private fun print(level: LogLevel, message: String, tag: String = "", throwable: Throwable? = null) {
        if (logFormat.occurrenceUsed || logFormat.tagFilterUsed || (logFormat.tagUsed && tag == "")) {
            val trace = getTrace()
            var newTag = tag
            if (tag == "")
                newTag = getTraceTag(trace)

            if (tagIsFiltered(tag))
                return

            logFormat.print(level, System.currentTimeMillis(), message, newTag, throwable, getTrace())
        } else
            logFormat.print(level, System.currentTimeMillis(), message, tag, throwable, null)
    }

    private fun getTraceTag(trace: StackTraceElement): String {
        val className = trace.className.split(".")
        return className[className.size - 1]
    }

    private fun getTrace(): StackTraceElement {
        return Exception().stackTrace[logFormat.traceSteps]
    }

    internal fun tagIsFiltered(tag: String): Boolean {
        return logFormat.tagFilterUsed && logFormat.filterTag != tag
    }
}