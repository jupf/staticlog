package de.jupf.staticlog.format

/**
 * @created 20.01.2016.
 * @author jpf
 */
class LogFormat() : Scope() {
    init {
        line(date("yyyy-MM-dd HH:mm:ss.SSS"), space, level, text("/"), tag, space(2), message, space(2), occurrence)
    }

    constructor(build: LogFormat.() -> Unit) : this() {
        children.clear()
        build()
    }

    internal var longTrace = false
    internal var androidOS: Boolean = when (System.getProperty("java.vm.vendor").equals("The Android Project")) {
        true -> {
            build {
                line(message, space(2), occurrence)
            }
            longTrace = true
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