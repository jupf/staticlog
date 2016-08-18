package de.jupf.staticlog.printer

import android.util.Log
import de.jupf.staticlog.core.LogLevel
import de.jupf.staticlog.format.LogFormat
import de.jupf.staticlog.format.TagBuilder
import java.io.OutputStream

/**
 * INSERT DESCRIPTION
 *
 * @created 25.04.2016
 * @author jpf
 */
class AndroidPrinter : Printer {
    internal val androidTag = TagBuilder()

    override fun print(level: LogLevel, time: Long, message: String, tag: String, throwable: Throwable?, trace: StackTraceElement?, logFormat: LogFormat) {
        val builder = StringBuilder()
        logFormat.buildString(level, time, message, tag, throwable, builder, trace, "")
        val tagBuilder = StringBuilder()
        androidTag.buildString(level, time, message, tag, throwable, tagBuilder, trace, "")

        if (throwable != null)
            when (level) {
                LogLevel.INFO -> Log.i(tagBuilder.toString(), builder.toString(), throwable)
                LogLevel.WARN -> Log.w(tagBuilder.toString(), builder.toString(), throwable)
                LogLevel.ERROR -> Log.e(tagBuilder.toString(), builder.toString(), throwable)
                LogLevel.DEBUG -> Log.d(tagBuilder.toString(), builder.toString(), throwable)
            }
        else
            when (level) {
                LogLevel.INFO -> Log.i(tagBuilder.toString(), builder.toString())
                LogLevel.WARN -> Log.w(tagBuilder.toString(), builder.toString())
                LogLevel.ERROR -> Log.e(tagBuilder.toString(), builder.toString())
                LogLevel.DEBUG -> Log.d(tagBuilder.toString(), builder.toString())
            }
    }
}