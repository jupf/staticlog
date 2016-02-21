package de.jupf.staticlog.core

import android.util.Log
import de.jupf.staticlog.format.LogFormat
import de.jupf.staticlog.format.TagBuilder

/**
 * The LogCore implements the core logging functionality.
 *
 * @author J.Pfeifer
 * @created 20.12.2015.
 */
enum class LogLevel {
    DEBUG, INFO, WARN, ERROR
}

internal val androidTag = TagBuilder()

internal fun debug(message: String, tag: String = "", exception: Exception? = null, logFormat: LogFormat) {
    printLogWhite(message,tag,exception,logFormat,LogLevel.DEBUG)
}

internal fun info(message: String, tag: String = "", exception: Exception? = null, logFormat: LogFormat) {
    printLogWhite(message,tag,exception,logFormat,LogLevel.INFO)
}

internal fun warn(message: String, tag: String = "", exception: Exception? = null, logFormat: LogFormat) {
    printLogRed(message,tag,exception,logFormat,LogLevel.WARN)
}

internal fun error(message: String, tag: String = "", exception: Exception? = null, logFormat: LogFormat) {
    printLogRed(message,tag,exception,logFormat,LogLevel.ERROR)
}

internal fun printLogWhite(message: String, tag: String, exception: Exception?, logFormat: LogFormat, level: LogLevel) {
    if(logFormat.occurrenceUsed || (logFormat.tagUsed && tag == "")) {
        val trace = getTrace(logFormat)
        var newTag = tag
        if(logFormat.tagUsed && tag == "")
             newTag = getTraceTag(trace)

        logFormat.printWhite(level, System.currentTimeMillis(), message, newTag, exception, getTrace(logFormat), logFormat)
    } else
        logFormat.printWhite(level, System.currentTimeMillis(), message, tag, exception, null, logFormat)
}

fun getTraceTag(trace: StackTraceElement): String {
    val className = trace.className.split(".")
    return className[className.size - 1]
}

internal fun printLogRed(message: String, tag: String, exception: Exception?, logFormat: LogFormat, level: LogLevel) {
    if(logFormat.occurrenceUsed || (logFormat.tagUsed && tag == "")) {
        val trace = getTrace(logFormat)
        var newTag = tag
        if(logFormat.tagUsed && tag == "")
            newTag = getTraceTag(trace)

        logFormat.printRed(level, System.currentTimeMillis(), message, newTag, exception, getTrace(logFormat), logFormat)
    } else
        logFormat.printRed(level, System.currentTimeMillis(), message, tag, exception, null, logFormat)
}

internal fun printOnAndroid(level: LogLevel, time: Long, message: String, tag: String, exception: Exception?, trace: StackTraceElement?, logFormat: LogFormat) {
    if(tagIsFiltered(tag,logFormat))
        return;
    val builder = StringBuilder()
    logFormat.buildString(level, time, message, tag, exception, builder, trace, "")
    val tagBuilder = StringBuilder()
    androidTag.buildString(level, time, message, tag, exception, tagBuilder, trace, "")

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

internal fun printWhiteLog(level: LogLevel, time: Long, message: String, tag: String, exception: Exception?, trace: StackTraceElement?, logFormat: LogFormat) {
    if(tagIsFiltered(tag, logFormat))
        return;
    val builder = StringBuilder()
    logFormat.buildString(level, time, message, tag, exception, builder, trace, "")
    exception?.buildString(level, time, message, tag, builder, trace, logFormat)
    printFlush(builder)
}

internal fun printRedLog(level: LogLevel, time: Long, message: String, tag: String, exception: Exception?, trace: StackTraceElement?, logFormat: LogFormat) {
    if(tagIsFiltered(tag, logFormat))
        return;
    val builder = StringBuilder()
    logFormat.buildString(level, time, message, tag, exception, builder, trace, "")
    exception?.buildString(level, time, message, tag, builder, trace, logFormat)
    printerrFlush(builder)
}

internal fun getTrace(logFormat: LogFormat): StackTraceElement {
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


internal fun Throwable.buildString(level: LogLevel, time: Long, message: String, tag: String, builder: StringBuilder, trace: StackTraceElement?, logFormat: LogFormat) {
    logFormat.exceptionFormat.buildString(level, time, message, tag, this, builder, trace, "")
}

internal fun tagIsFiltered(tag: String, logFormat: LogFormat) : Boolean {
    return logFormat.tagFilterUsed && logFormat.filterTag != tag
}

