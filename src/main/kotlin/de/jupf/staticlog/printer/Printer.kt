package de.jupf.staticlog.printer

import de.jupf.staticlog.core.LogLevel
import de.jupf.staticlog.format.LogFormat
import java.io.File
import java.io.OutputStream

/**
 * INSERT DESCRIPTION
 *
 * @created 25.04.2016
 * @author jpf
 */
interface Printer {
    fun print(level: LogLevel, time: Long, message: String, tag: String, throwable: Throwable?, trace: StackTraceElement?, logFormat: LogFormat)
}