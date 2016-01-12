package de.jupf.staticlog.core

import de.jupf.staticlog.format.Scope

/**
* Created on 20.12.2015.
*
* @author J.Pfeifer
*/
enum class LogLevel {
    INFO,WARN,ERROR,DEBUG
}

internal object LogCore {
    fun info(time: Long, log: String, tag: String, exception: Exception?) {
        printWhiteLog(LogLevel.INFO, time, log, tag, exception)
    }

    fun warn(time: Long, log: String, tag: String, exception: Exception?) {
        printRedLog(LogLevel.WARN, time, log, tag, exception)
    }

    fun error(time: Long, log: String, tag: String, exception: Exception?) {
        printRedLog(LogLevel.ERROR, time, log, tag, exception)
    }

    fun debug(time: Long, log: String, tag: String, exception: Exception?) {
        printWhiteLog(LogLevel.DEBUG, time, log, tag, exception)
    }

    private fun printWhiteLog(level: LogLevel, time: Long, message: String, tag: String, exception: Exception?) {
        val builder = StringBuilder()
        LogFormat.buildString(level, time, message, tag, exception, builder, "")
        exception?.buildString(level, time, message, tag, builder)
        printFlush(builder)
    }

    private fun printRedLog(level: LogLevel, time: Long, message: String, tag: String, exception: Exception?) {
        val builder = StringBuilder()
        LogFormat.buildString(level, time, message, tag, exception, builder, "")
        exception?.buildString(level, time, message, tag, builder)
        printerrFlush(builder)
    }

    fun getTrace(): String {
        if (LogFormat.calledFromJava)
            return Exception().stackTrace[3].toString()
        return Exception().stackTrace[2].toString()
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

object LogFormat : Scope() {
    internal var calledFromJava = false

    init {
        line(date("yyyy-MM-dd HH:mm:ss"),space,level,space(2),message,space(2),tag)
    }
}

internal object ExceptionFormat : Scope() {
    init {
        indent {
            indent {
                line(exception)
            }
        }
    }
}

internal fun Throwable.buildString(level: LogLevel, time: Long, message: String, tag: String,builder: StringBuilder) {
    ExceptionFormat.buildString(level,time,message,tag,this,builder,"")
}