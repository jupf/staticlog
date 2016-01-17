package de.jupf.staticlog.core

import de.jupf.staticlog.Log
import de.jupf.staticlog.format.Scope

/**
 * Created on 20.12.2015.
 *
 * @author J.Pfeifer
 */
enum class LogLevel {
    DEBUG, INFO, WARN, ERROR
}

internal object LogCore {
    var androidOS: Boolean = when (System.getProperty("java.vm.vendor").equals("The Android Project")) {
        true -> {
            Log.Log.format {
                line(message,space(2),occurrence)
            }
            LogFormat.calledFromJava = true
            true
        }
        false -> false
    }
    val androidTag = LogFormat.tag

    fun info(log: String, tag: String, exception: Exception?, trace: StackTraceElement) {
        if (androidOS) {
            printOnAndroid(LogLevel.INFO, log, tag, exception, trace)
        } else
            printWhiteLog(LogLevel.INFO, System.currentTimeMillis(), log, tag, exception, trace)
    }


    fun warn(log: String, tag: String, exception: Exception?, trace: StackTraceElement) {
        if (androidOS) {
            printOnAndroid(LogLevel.WARN, log, tag, exception, trace)
        } else
            printRedLog(LogLevel.WARN, System.currentTimeMillis(), log, tag, exception, trace)
    }

    fun error(log: String, tag: String, exception: Exception?, trace: StackTraceElement) {
        if (androidOS) {
            printOnAndroid(LogLevel.ERROR, log, tag, exception, trace)
        } else
            printRedLog(LogLevel.ERROR, System.currentTimeMillis(), log, tag, exception, trace)
    }

    fun debug(log: String, tag: String, exception: Exception?, trace: StackTraceElement) {
        if (androidOS) {
            printOnAndroid(LogLevel.DEBUG, log, tag, exception, trace)
        } else
            printWhiteLog(LogLevel.DEBUG, System.currentTimeMillis(), log, tag, exception, trace)
    }

    private fun printOnAndroid(level: LogLevel, log: String, tag: String, exception: Exception?, trace: StackTraceElement) {
        val builder = StringBuilder()
        LogFormat.buildString(level, 0L, log, tag, exception, builder, trace, "")
        val tagBuilder = StringBuilder()
        androidTag.buildString(level,0L,log,tag,exception,tagBuilder,trace,"")

        if (exception != null)
            when (level) {
                LogLevel.INFO -> android.util.Log.i(tagBuilder.toString(), builder.toString(), exception)
                LogLevel.WARN -> android.util.Log.w(tagBuilder.toString(), builder.toString(), exception)
                LogLevel.ERROR -> android.util.Log.e(tagBuilder.toString(), builder.toString(), exception)
                LogLevel.DEBUG -> android.util.Log.d(tagBuilder.toString(), builder.toString(), exception)
            }
        else
            when (level) {
                LogLevel.INFO -> android.util.Log.i(tagBuilder.toString(), builder.toString())
                LogLevel.WARN -> android.util.Log.w(tagBuilder.toString(), builder.toString())
                LogLevel.ERROR -> android.util.Log.e(tagBuilder.toString(), builder.toString())
                LogLevel.DEBUG -> android.util.Log.d(tagBuilder.toString(), builder.toString())
            }
    }

    private fun printWhiteLog(level: LogLevel, time: Long, message: String, tag: String, exception: Exception?, trace: StackTraceElement) {
        val builder = StringBuilder()
        LogFormat.buildString(level, time, message, tag, exception, builder, trace, "")
        exception?.buildString(level, time, message, tag, builder, trace)
        printFlush(builder)
    }

    private fun printRedLog(level: LogLevel, time: Long, message: String, tag: String, exception: Exception?, trace: StackTraceElement) {
        val builder = StringBuilder()
        LogFormat.buildString(level, time, message, tag, exception, builder, trace, "")
        exception?.buildString(level, time, message, tag, builder, trace)
        printerrFlush(builder)
    }

    fun getTrace(): StackTraceElement {
        if (LogFormat.calledFromJava)
            return Exception().stackTrace[3]
        return Exception().stackTrace[2]
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
        line(date("yyyy-MM-dd HH:mm:ss.SSS"), space, level, text("/"), tag, space(2), message, space(2), occurrence)
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

internal fun Throwable.buildString(level: LogLevel, time: Long, message: String, tag: String, builder: StringBuilder, trace: StackTraceElement) {
    ExceptionFormat.buildString(level, time, message, tag, this, builder, trace, "")
}