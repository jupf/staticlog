package de.jupf.staticlog.format

import de.jupf.staticlog.core.LogLevel
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.collections.indices
import kotlin.text.split

/**
 * @author J.Pfeifer
 * @created 20.12.2015
 */

interface Builder {
    fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement, indent: String)
}

open class TextBuilder(val text: String) : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement, indent: String) {
        builder.append(text)
    }
}

class MessageBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement, indent: String) {
        builder.append(message)
    }
}

class LevelBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement, indent: String) {
        builder.append(logLevel.toString())
    }
}

class EpochBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement, indent: String) {
        builder.append(time)
    }
}

class DateBuilder(format: String) : Builder {
    val dateFormat = SimpleDateFormat(format)

    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement, indent: String) {
        builder.append(dateFormat.format(Date(time)))
    }
}

class TagBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement, indent: String) {
        if (!tag.equals(""))
            builder.append("$tag")
        else {
            val className = trace.className.split(".")
            builder.append("${className[className.size - 1]}")
        }
    }
}

class OccurrenceBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement, indent: String) {
        builder.append("[${trace.toString()}]")
    }
}

internal class ExceptionBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement, indent: String) {
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







