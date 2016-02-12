package de.jupf.staticlog.core

import android.util.Log
import de.jupf.staticlog.format.LogFormat
import de.jupf.staticlog.format.TagBuilder

/**
 * The LogCore Object implements the core logging functionality.
 *
 * @author J.Pfeifer
 * @created 20.12.2015.
 */
enum class LogLevel {
    DEBUG, INFO, WARN, ERROR
}

internal object LogCore {
    val androidTag = TagBuilder()


    fun info(log: String, tag: String, exception: Exception?, trace: StackTraceElement, logFormat: LogFormat) {
        if (logFormat.androidOS) {
            printOnAndroid(LogLevel.INFO, log, tag, exception, trace, logFormat)
        } else
            printWhiteLog(LogLevel.INFO, System.currentTimeMillis(), log, tag, exception, trace, logFormat)
    }

    fun warn(log: String, tag: String, exception: Exception?, trace: StackTraceElement, logFormat: LogFormat) {
        if (logFormat.androidOS) {
            printOnAndroid(LogLevel.WARN, log, tag, exception, trace, logFormat)
        } else
            printRedLog(LogLevel.WARN, System.currentTimeMillis(), log, tag, exception, trace, logFormat)
    }

    fun error(log: String, tag: String, exception: Exception?, trace: StackTraceElement, logFormat: LogFormat) {
        if (logFormat.androidOS) {
            printOnAndroid(LogLevel.ERROR, log, tag, exception, trace, logFormat)
        } else
            printRedLog(LogLevel.ERROR, System.currentTimeMillis(), log, tag, exception, trace, logFormat)
    }

    fun debug(log: String, tag: String, exception: Exception?, trace: StackTraceElement, logFormat: LogFormat) {
        if (logFormat.androidOS) {
            printOnAndroid(LogLevel.DEBUG, log, tag, exception, trace, logFormat)
        } else
            printWhiteLog(LogLevel.DEBUG, System.currentTimeMillis(), log, tag, exception, trace, logFormat)
    }

    private fun printOnAndroid(level: LogLevel, log: String, tag: String, exception: Exception?, trace: StackTraceElement, logFormat: LogFormat) {
        val builder = StringBuilder()
        logFormat.buildString(level, 0L, log, tag, exception, builder, trace, "")
        val tagBuilder = StringBuilder()
        androidTag.buildString(level, 0L, log, tag, exception, tagBuilder, trace, "")

        if (exception != null)
            when (level) {
                LogLevel.INFO -> Log.i(tagBuilder.toString(), builder.toString(), exception)
                LogLevel.WARN -> Log.w(tagBuilder.toString(), builder.toString(), exception)
                LogLevel.ERROR -> Log.e(tagBuilder.toString(), builder.toString(), exception)
                LogLevel.DEBUG -> Log.d(tagBuilder.toString(), builder.toString(), exception)
            }
        else
            when (level) {
                LogLevel.INFO -> Log.i(tagBuilder.toString(), builder.toString())
                LogLevel.WARN -> Log.w(tagBuilder.toString(), builder.toString())
                LogLevel.ERROR -> Log.e(tagBuilder.toString(), builder.toString())
                LogLevel.DEBUG -> Log.d(tagBuilder.toString(), builder.toString())
            }
    }

    private fun printWhiteLog(level: LogLevel, time: Long, message: String, tag: String, exception: Exception?, trace: StackTraceElement, logFormat: LogFormat) {
        val builder = StringBuilder()
        logFormat.buildString(level, time, message, tag, exception, builder, trace, "")
        exception?.buildString(level, time, message, tag, builder, trace, logFormat)
        printFlush(builder)
    }

    private fun printRedLog(level: LogLevel, time: Long, message: String, tag: String, exception: Exception?, trace: StackTraceElement, logFormat: LogFormat) {
        val builder = StringBuilder()
        logFormat.buildString(level, time, message, tag, exception, builder, trace, "")
        exception?.buildString(level, time, message, tag, builder, trace, logFormat)
        printerrFlush(builder)
    }

    fun getTrace(logFormat: LogFormat): StackTraceElement {
        return Exception().stackTrace[logFormat.traceSteps]
    }

    internal fun printerrFlush(message: Any?) {
        System.err.print(message);
        System.err.flush()
    }

    internal fun printFlush(message: Any?) {
        System.out.print(message);
        System.out.flush()
    }
}


internal fun Throwable.buildString(level: LogLevel, time: Long, message: String, tag: String, builder: StringBuilder, trace: StackTraceElement, logFormat: LogFormat) {
    logFormat.exceptionFormat.buildString(level, time, message, tag, this, builder, trace, "")
}