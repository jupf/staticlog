package de.jupf.staticlog.format

import de.jupf.staticlog.core.LogLevel
import de.jupf.staticlog.printer.AndroidPrinter
import de.jupf.staticlog.printer.DesktopPrinter
import de.jupf.staticlog.printer.Printer
import java.io.FileOutputStream
import java.util.*

/**
 * @author J.Pfeifer
 * @created 20.01.2016
 */
class LogFormat : Scope() {

    internal var traceSteps = 3
    internal val printer: MutableList<Printer> = ArrayList()

    internal var tagFilterUsed = false
    internal var filterTag = "" // Only messages with this tag will be printed.
        set(value) {
            if (value == "") {
                tagFilterUsed = false
            } else {
                tagFilterUsed = true
                field = value
            }
        }

    private val defaultPrinter: Printer

    init {
        line(date("yyyy-MM-dd HH:mm:ss.SSS"), space, level, text("/"), tag, space(2), message, space(2), occurrence)

        if (System.getProperty("java.vm.vendor") == "The Android Project") {
            build {
                line(message, space(2), occurrence)
            }
            traceSteps = 4
            defaultPrinter = AndroidPrinter()
        } else {
            defaultPrinter = DesktopPrinter()
        }
        printer.add(defaultPrinter)
    }

    internal var exceptionFormat = Scope {
        indent {
            indent {
                line(exception)
            }
        }
    }

    internal fun print(level: LogLevel, time: Long, message: String, tag: String = "", throwable: Throwable? = null, traceElement: StackTraceElement?) {
        printer.forEach { it.print(level, time, message, tag, throwable, traceElement, this) }
    }

    internal fun build(build: LogFormat.() -> Unit) {
        children.clear()
        occurrenceUsed = false
        tagUsed = System.getProperty("java.vm.vendor").equals("The Android Project")
        build()
        for (i in children.indices) {
            val child = children[i]
            if (child is Scope) {
                if (child.occurrenceUsed)
                    occurrenceUsed = true
                if (child.tagUsed)
                    tagUsed = true
            }
        }
    }

    internal fun setDefaultPrinter() {
        printer.clear()
        printer.add(defaultPrinter)
    }


}