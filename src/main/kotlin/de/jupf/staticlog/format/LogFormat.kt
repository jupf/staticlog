package de.jupf.staticlog.format

import de.jupf.staticlog.core.printOnAndroid
import de.jupf.staticlog.core.printRedLog
import de.jupf.staticlog.core.printWhiteLog

/**
 * @author J.Pfeifer
 * @created 20.01.2016
 */
class LogFormat() : Scope() {

    internal var traceSteps = 3
    internal var printWhite = ::printWhiteLog
    internal var printRed = ::printRedLog

    internal var tagFilterUsed = false
    internal var filterTag = "" // Only messages with this tag will be printed.
        set(value) {
            if(value == "") {
                tagFilterUsed = false
            } else {
                tagFilterUsed = true
                field = value
            }
        }

    init {
        line(date("yyyy-MM-dd HH:mm:ss.SSS"), space, level, text("/"), tag, space(2), message, space(2), occurrence)

        when (System.getProperty("java.vm.vendor").equals("The Android Project")) {
            true -> {
                build {
                    line(message, space(2), occurrence)
                }
                traceSteps = 4
                printWhite = ::printOnAndroid
                printRed = ::printOnAndroid
            }
        }
    }

    internal var exceptionFormat = Scope {
        indent {
            indent {
                line(exception)
            }
        }
    }

    internal fun build(build: LogFormat.() -> Unit) {
        children.clear()
        occurrenceUsed = false
        tagUsed = false
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
}