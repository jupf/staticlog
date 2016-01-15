package de.jupf.staticlog

import de.jupf.staticlog.core.LogCore
import de.jupf.staticlog.core.LogFormat
import de.jupf.staticlog.core.LogLevel
import de.jupf.staticlog.format.Builder
import de.jupf.staticlog.format.Format
import de.jupf.staticlog.format.Indent
import de.jupf.staticlog.format.Line
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.collections.indices
import kotlin.concurrent.read
import kotlin.concurrent.write


/**
 * Created on 20.12.2015.
 *
 * @author J.Pfeifer
 */
class Log {
    /**
     * Main StaticLog logging interface
     */
    companion object Log{
        val lock = ReentrantReadWriteLock()
        @JvmStatic var logLevel = LogLevel.DEBUG
            set(value) = lock.write { field = value }

        @JvmStatic
        fun info(message: String, tag: String, exception: Exception?) {
            if(!checkLogLevel(LogLevel.INFO)) return;
            val trace = LogCore.getTrace()
            LogCore.info(message, tag, exception, trace)
        }

        @JvmStatic
        fun warn(message: String, tag: String, exception: Exception?) {
            if(!checkLogLevel(LogLevel.WARN)) return
            val trace = LogCore.getTrace()
            LogCore.warn(message, tag, exception, trace)
        }

        @JvmStatic
        fun error(message: String, tag: String, exception: Exception?) {
            val trace = LogCore.getTrace()
            LogCore.error(message, tag, exception, trace)
        }

        @JvmStatic
        fun debug(message: String, tag: String, exception: Exception?) {
            if(!checkLogLevel(LogLevel.DEBUG)) return
            val trace = LogCore.getTrace()
            LogCore.debug(message, tag, exception, trace)
        }

        fun format(build: LogFormat.() -> Unit) {
            LogFormat.children.clear()
            LogFormat.build()
        }

        private fun checkLogLevel(level: LogLevel) = lock.read{logLevel <= level}

        ///////////////////////////////////////////////////////////////////////////////////
        // Needing overloaded functions for java.. or the getTrace() call would go wrong //
        // additionally: the auto generated overloads had no parameter names in java...  //
        ///////////////////////////////////////////////////////////////////////////////////

        @JvmStatic
        fun info(message: String, exception: Exception?) {
            if(!checkLogLevel(LogLevel.INFO)) return;
            val trace = LogCore.getTrace()
            LogCore.info(message, "", exception,trace)
        }

        @JvmStatic
        fun info(message: String, tag: String) {
            if(!checkLogLevel(LogLevel.INFO)) return;
            val trace = LogCore.getTrace()
            LogCore.info(message, tag, null, trace)
        }

        @JvmStatic
        fun info(message: String) {
            if(!checkLogLevel(LogLevel.INFO)) return;
            val trace = LogCore.getTrace()
            LogCore.info(message, "", null, trace)
        }

        @JvmStatic
        fun warn(message: String, exception: Exception?) {
            if(!checkLogLevel(LogLevel.WARN)) return;
            val trace = LogCore.getTrace()
            LogCore.warn(message, "", exception, trace)
        }

        @JvmStatic
        fun warn(message: String, tag: String) {
            if(!checkLogLevel(LogLevel.WARN)) return;
            val trace = LogCore.getTrace()
            LogCore.warn(message, tag, null, trace)
        }

        @JvmStatic
        fun warn(message: String) {
            if(!checkLogLevel(LogLevel.WARN)) return;
            val trace = LogCore.getTrace()
            LogCore.warn(message, "", null, trace)
        }

        @JvmStatic
        fun debug(message: String, exception: Exception?) {
            if(!checkLogLevel(LogLevel.DEBUG)) return;
            val trace = LogCore.getTrace()
            LogCore.debug(message, "", exception, trace)
        }

        @JvmStatic
        fun debug(message: String, tag: String) {
            if(!checkLogLevel(LogLevel.DEBUG)) return;
            val trace = LogCore.getTrace()
            LogCore.debug(message, tag, null, trace)
        }

        @JvmStatic
        fun debug(message: String) {
            if(!checkLogLevel(LogLevel.DEBUG)) return;
            val trace = LogCore.getTrace()
            LogCore.debug(message, "", null, trace)
        }

        @JvmStatic
        fun error(message: String, exception: Exception?) {
            val trace = LogCore.getTrace()
            LogCore.error(message, "", exception, trace)
        }

        @JvmStatic
        fun error(message: String, tag: String) {
            val trace = LogCore.getTrace()
            LogCore.error(message, tag, null, trace)
        }

        @JvmStatic
        fun error(message: String) {
            val trace = LogCore.getTrace()
            LogCore.error(message, "", null, trace)
        }
    }

    /**
     * Object for the Java StaticLog format interface
     */
    object format {
        @JvmStatic
        fun create(): Format {
            LogFormat.calledFromJava = true
            LogFormat.children.clear()
            val format = Format()
            LogFormat.children.add(format)
            return format
        }

        @JvmStatic
        fun space(times: Int): Builder {
            return LogFormat.space(times)
        }

        @JvmStatic
        fun tab(times: Int): Builder {
            return LogFormat.tab(times)
        }

        @JvmStatic
        fun date(format: String): Builder {
            return LogFormat.date(format)
        }

        @JvmStatic
        fun epoch(): Builder {
            return LogFormat.epoch
        }

        @JvmStatic
        fun level(): Builder {
            return LogFormat.level
        }

        @JvmStatic
        fun message(): Builder {
            return LogFormat.message
        }

        @JvmStatic
        fun tag(): Builder {
            return LogFormat.tag
        }

        @JvmStatic
        fun occurrence(): Builder {
            return LogFormat.occurrence
        }

        @JvmStatic
        fun line(vararg builders: Builder): Builder {
            val line = Line()
            for (i in builders.indices) {
                line.children.add(builders[i])
            }
            return line
        }

        @JvmStatic
        fun indent(vararg builders: Builder): Builder {
            val indent = Indent()
            for (i in builders.indices) {
                indent.children.add(builders[i])
            }
            return indent
        }
    }
}
