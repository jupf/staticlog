package de.jupf.staticlog.format

/**
 * @author J.Pfeifer
 * @created 20.01.2016
 */
class LogFormat() : Scope() {
    init {
        line(date("yyyy-MM-dd HH:mm:ss.SSS"), space, level, text("/"), tag, space(2), message, space(2), occurrence)
    }

    constructor(build: LogFormat.() -> Unit) : this() {
        children.clear()
        build()
    }

    internal var traceSteps = 3
    internal var androidOS: Boolean = when (System.getProperty("java.vm.vendor").equals("The Android Project")) {
        true -> {
            build {
                line(message, space(2), occurrence)
            }
            traceSteps = 4
            true
        }
        false -> false
    }
    internal var exceptionFormat = Scope {
        indent {
            indent {
                line(exception)
            }
        }
    }

    public fun build(build: LogFormat.() -> Unit) {
        children.clear()
        build()
    }
}