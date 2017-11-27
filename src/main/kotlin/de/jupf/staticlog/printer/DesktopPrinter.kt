package de.jupf.staticlog.printer

import de.jupf.staticlog.core.LogLevel
import de.jupf.staticlog.format.LogFormat
import java.util.*

/**
 * @created 25.04.2016
 * @author J.Pfeifer
 */
class DesktopPrinter : Printer {
    override fun print(level: LogLevel, time: Long, message: String, tag: String, throwable: Throwable?, trace: StackTraceElement?, logFormat: LogFormat) {
        val builder = StringBuilder()
        logFormat.buildString(level, time, message, tag, throwable, builder, trace, "")
        throwable?.buildString(level, time, message, tag, builder, trace, logFormat)
        when(level) {
            LogLevel.DEBUG, LogLevel.INFO  -> printFlush(builder)
            LogLevel.WARN, LogLevel.ERROR  -> printerrFlush(builder)
        }
    }

    internal fun printerrFlush(message: Any?) {
        System.err.print(message)
        System.err.flush()
    }

    internal fun printFlush(message: Any?) {
        System.out.print(message)
        System.out.flush()
    }

    internal fun Throwable.buildString(level: LogLevel, time: Long, message: String, tag: String, builder: StringBuilder, trace: StackTraceElement?, logFormat: LogFormat) {
        logFormat.exceptionFormat.buildString(level, time, message, tag, this, builder, trace, "")
    }
}
