package de.jupf.staticlog.format

import de.jupf.staticlog.core.LogLevel
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

/**
 * @author J.Pfeifer
 * @created 20.12.2015.
 */
open class Scope() : Builder {

    constructor(init: Scope.() -> Unit) : this() {
        this.init()
    }

    internal var occurrenceUsed = false
    internal var tagUsed = false

    internal val children = CopyOnWriteArrayList<Builder>()

    val level: Builder
        get() = LevelBuilder()

    val message: Builder
        get() = MessageBuilder()

    val tag: Builder
        get() {
            tagUsed = true
            return TagBuilder()
        }

    val occurrence: Builder
        get() {
            occurrenceUsed = true
            return OccurrenceBuilder()
        }

    val tab: Builder
        get() = TextBuilder("\t")

    val space: Builder
        get() = TextBuilder(" ")

    val epoch: Builder
        get() = EpochBuilder()

    internal val exception: Builder
        get() = ExceptionBuilder()

    internal fun line(init: Line.() -> Unit) = initScope(Line(), init)
    fun indent(init: Indent.() -> Unit) = initScope(Indent(), init)

    fun line(vararg builders: Builder) {
        line {
            for (i in builders.indices) {
                this.children.add(builders[i])
            }
        }
    }

    fun indentLine(vararg builders: Builder) {
        indent {
            line {
                for (i in builders.indices) {
                    this.children.add(builders[i])
                }
            }
        }
    }

    fun indent(vararg builders: Builder) {
        indent {
            for (i in builders.indices) {
                this.children.add(builders[i])
            }
        }
    }

    fun tab(times: Int): Builder {
        var tabString = ""
        for (i in 1..times) {
            tabString += "\t"
        }
        val tabs = TextBuilder(tabString)
        return tabs
    }

    fun space(times: Int): Builder {
        var tabString = ""
        for (i in 1..times) {
            tabString += " "
        }
        val tabs = TextBuilder(tabString)
        return tabs
    }

    fun date(format: String = "", locale: Locale = Locale.getDefault(), timeZone: TimeZone = TimeZone.getDefault(), clockFunction: (Long) -> Long = DateBuilder.USE_LOG_TIME): Builder {
        return DateBuilder(format, locale, timeZone, clockFunction)
    }

    fun text(text: String): Builder {
        return TextBuilder(text)
    }

    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement?, indent: String) {
        for (child in children) {
            child.buildString(logLevel, time, message, tag, exception, builder, trace, indent)
        }
    }

    protected fun <T : Builder> initScope(scope: T, init: T.() -> Unit): T {
        scope.init()
        children.add(scope)
        return scope
    }
}

class Line : Scope() {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement?, indent: String) {
        if (!indent.equals(""))
            builder.append(indent)
        for (child in children) {
            child.buildString(logLevel, time, message, tag, exception, builder, trace, indent)
        }

        builder.appendln()
    }
}

class Indent : Scope() {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Throwable?, builder: StringBuilder, trace: StackTraceElement?, indent: String) {
        for (child in children) {
            child.buildString(logLevel, time, message, tag, exception, builder, trace, indent + "   ")
        }
    }
}
