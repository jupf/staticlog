package de.jupf.staticlog

import de.jupf.staticlog.core.LogCore
import de.jupf.staticlog.core.LogFormat
import de.jupf.staticlog.format.Builder
import de.jupf.staticlog.format.Format
import de.jupf.staticlog.format.Indent
import de.jupf.staticlog.format.Line


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
        @JvmStatic
        fun info(message: String, tag: String = LogCore.getTrace(), exception: Exception? = null) {
            LogCore.info(System.currentTimeMillis(), message, tag, exception)
        }

        @JvmStatic
        fun warn(message: String, tag: String = LogCore.getTrace(), exception: Exception? = null) {
            LogCore.warn(System.currentTimeMillis(), message, tag, exception)
        }

        @JvmStatic
        fun error(message: String, tag: String = LogCore.getTrace(), exception: Exception? = null) {
            LogCore.error(System.currentTimeMillis(), message, tag, exception)
        }

        @JvmStatic
        fun debug(message: String, tag: String = LogCore.getTrace(), exception: Exception? = null) {
            LogCore.debug(System.currentTimeMillis(), message, tag, exception)
        }


        fun format(build: LogFormat.() -> Unit) {
            LogFormat.children.clear()
            LogFormat.build()
        }

        ///////////////////////////////////////////////////////////////////////////////////
        // Needing overloaded functions for java.. or the getTrace() call would go wrong //
        // additionally: the auto generated overloads had no parameter names in java...  //
        ///////////////////////////////////////////////////////////////////////////////////

        @JvmStatic
        fun info(message: String, exception: Exception?) {
            val tag = LogCore.getTrace()
            LogCore.info(System.currentTimeMillis(), message, tag, exception)
        }

        @JvmStatic
        fun info(message: String) {
            val tag = LogCore.getTrace()
            val exception: Exception? = null
            LogCore.info(System.currentTimeMillis(), message, tag, exception)
        }

        @JvmStatic
        fun warn(message: String, exception: Exception?) {
            val tag = LogCore.getTrace()
            LogCore.warn(System.currentTimeMillis(), message, tag, exception)
        }

        @JvmStatic
        fun warn(message: String) {
            val tag = LogCore.getTrace()
            val exception: Exception? = null
            LogCore.warn(System.currentTimeMillis(), message, tag, exception)
        }

        @JvmStatic
        fun debug(message: String, exception: Exception?) {
            val tag = LogCore.getTrace()
            LogCore.debug(System.currentTimeMillis(), message, tag, exception)
        }

        @JvmStatic
        fun debug(message: String) {
            val tag = LogCore.getTrace()
            val exception: Exception? = null
            LogCore.debug(System.currentTimeMillis(), message, tag, exception)
        }

        @JvmStatic
        fun error(message: String, exception: Exception?) {
            val tag = LogCore.getTrace()
            LogCore.error(System.currentTimeMillis(), message, tag, exception)
        }

        @JvmStatic
        fun error(message: String) {
            val tag = LogCore.getTrace()
            val exception: Exception? = null
            LogCore.error(System.currentTimeMillis(), message, tag, exception)
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
