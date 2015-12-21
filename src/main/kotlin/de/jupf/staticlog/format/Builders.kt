package de.jupf.staticlog.format

import de.jupf.staticlog.core.LogLevel
import java.text.SimpleDateFormat
import java.util.Date

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

interface Builder {
    fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Exception?, builder: StringBuilder, indent: String)
}

internal class TextBuilder(val text: String) : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Exception?, builder: StringBuilder, indent: String) {
        builder.append(text)
    }
}

abstract class MessageBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Exception?, builder: StringBuilder, indent: String){
        builder.append(message)
    }
}

abstract class LevelBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Exception?, builder: StringBuilder, indent: String){
        builder.append(logLevel.toString())
    }
}

abstract class EpochBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Exception?, builder: StringBuilder, indent: String){
        builder.append(time)
    }
}

abstract class DateBuilder(format: String) : Builder {
    val dateFormat = SimpleDateFormat(format)

    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Exception?, builder: StringBuilder, indent: String){
        builder.append(dateFormat.format(Date(time)))
    }
}

abstract class TagBuilder : Builder {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Exception?, builder: StringBuilder, indent: String){
        builder.append("[$tag]")
    }
}







