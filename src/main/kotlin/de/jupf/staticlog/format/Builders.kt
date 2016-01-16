package de.jupf.staticlog.format

import de.jupf.staticlog.core.LogLevel
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.collections.indices
import kotlin.text.split

/**
 * Created on 20.12.2015.
 *
 * @author J.Pfeifer
 */

class Level : LevelBuilder()

class Message : MessageBuilder()
class Tag : TagBuilder()
class Epoch : EpochBuilder()
class Date(format: String) : DateBuilder(format)
class Occurrence() : OccurrenceBuilder()
class Text(text: String) : TextBuilder(text)
internal class ExceptionB : ExceptionBuilder()

interface Builder {
    fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement, indent: String)
}

open internal class TextBuilder(val text: String) : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement, indent: String) {
        builder.append(text)
    }
}

abstract class MessageBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement, indent: String) {
        builder.append(message)
    }
}

abstract class LevelBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement, indent: String) {
        builder.append(logLevel.toString())
    }
}

abstract class EpochBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement, indent: String) {
        builder.append(time)
    }
}

abstract class DateBuilder(format: String) : Builder {
    val dateFormat = SimpleDateFormat(format)

    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement, indent: String) {
        builder.append(dateFormat.format(Date(time)))
    }
}

abstract class TagBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement, indent: String) {
        if(!tag.equals(""))
            builder.append("$tag")
        else {
            val className = trace.className.split(".")
            builder.append("${className[className.size-1]}")
        }
    }
}

abstract class OccurrenceBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement, indent: String) {
        builder.append("[${trace.toString()}]")
    }
}

abstract class ExceptionBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement, indent: String) {
        if (exception != null) {
            builder.append(exception.javaClass.name + ": " + exception.message + "\n" + indent + "   ")
            for (i in exception.stackTrace.indices) {
                builder.append("at " + exception.stackTrace[i].toString() + "\n" + indent + "   ")
            }
            if (exception.cause != null) {
                builder.setLength(builder.length-3)
                builder.append("Caused by: "+exception.cause!!.javaClass.name + ": " + exception.cause!!.message + "\n" + indent + "   ")
                for (i in exception.stackTrace.indices) {
                    builder.append("at " + exception.stackTrace[i].toString() + "\n" + indent + "   ")
                }
            } else {
                builder.setLength(builder.length-(indent.length+3))
            }
        }
    }
}







