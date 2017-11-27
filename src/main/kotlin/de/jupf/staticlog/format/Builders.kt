package de.jupf.staticlog.format

import de.jupf.staticlog.core.LogLevel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.indices

/**
 * @author J.Pfeifer
 * @created 20.12.2015
 */

interface Builder {
    fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement?, indent: String)
}

open class TextBuilder(val text: String) : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement?, indent: String) {
        builder.append(text)
    }
}

class MessageBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement?, indent: String) {
        builder.append(message)
    }
}

class LevelBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement?, indent: String) {
        builder.append(logLevel.toString())
    }
}

class EpochBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement?, indent: String) {
        builder.append(time)
    }
}

class DateBuilder(format: String, locale: Locale = Locale.getDefault(), timeZone: TimeZone = TimeZone.getDefault(),
                  val clockFunction: (Long) -> Long = USE_LOG_TIME) : Builder {
    companion object {
        val USE_LOG_TIME = { globalTime: Long -> globalTime }
    }
    private val dateFormat: SimpleDateFormat = SimpleDateFormat(if (format == "") "yyyy-MM-dd HH:mm:ss.SSS" else format, locale)
    init {
        dateFormat.timeZone = timeZone
    }

    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement?, indent: String) {
        builder.append(dateFormat.format(Date(clockFunction(time))))
    }
}

class TagBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement?, indent: String) {
        builder.append(tag)
    }
}

class OccurrenceBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement?, indent: String) {
        builder.append("[${trace.toString()}]")
    }
}

internal class ExceptionBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement?, indent: String) {
        if (exception != null) {
            builder.append(exception.javaClass.name + ": " + exception.message + "\n" + indent + "   ")
            for (i in exception.stackTrace.indices) {
                builder.append("at " + exception.stackTrace[i].toString() + "\n" + indent + "   ")
            }
            if (exception.cause != null) {
                builder.setLength(builder.length - 3)
                builder.append("Caused by: " + exception.cause!!.javaClass.name + ": " + exception.cause!!.message + "\n" + indent + "   ")
                for (i in exception.stackTrace.indices) {
                    builder.append("at " + exception.stackTrace[i].toString() + "\n" + indent + "   ")
                }
            }
            builder.setLength(builder.length - (indent.length + 4))
        }
    }
}







