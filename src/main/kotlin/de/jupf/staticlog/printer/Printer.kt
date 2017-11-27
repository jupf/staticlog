package de.jupf.staticlog.printer

import de.jupf.staticlog.core.LogLevel
import de.jupf.staticlog.format.LogFormat
import java.util.*

/**
 * @created 25.04.2016
 * @author J.Pfeifer
 */
interface Printer {
    fun print(level: LogLevel, time: Long, message: String, tag: String, throwable: Throwable?, trace: StackTraceElement?, logFormat: LogFormat)
}