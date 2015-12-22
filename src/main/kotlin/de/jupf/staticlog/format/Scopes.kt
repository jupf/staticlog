package de.jupf.staticlog.format

import de.jupf.staticlog.core.LogLevel

/**
 * Created on 20.12.2015.
 *
 * @author J.Pfeifer
 */
class Line : LineScope()

class Indent : IndentScope()
class Format : Scope()

abstract class Scope : Builder {
    val children = arrayListOf<Builder>()

    val level: Builder
        get() = Level()

    val message: Builder
        get() = Message()

    val tag: Builder
        get() = Tag()

    val tab: Builder
        get() = TextBuilder("\t")

    val space: Builder
        get() = TextBuilder(" ")

    val epoch: Builder
        get() = Epoch()

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

    fun date(format: String): Builder {
        return Date(format)
    }

    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Exception?, builder: StringBuilder, indent: String) {
        for (i in children.indices) {
            children[i].buildString(logLevel, time, message, tag, exception, builder, indent)
        }
    }

    protected fun <T : Builder> initScope(scope: T, init: T.() -> Unit): T {
        scope.init()
        children.add(scope)
        return scope
    }
}

abstract class LineScope : Scope() {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Exception?, builder: StringBuilder, indent: String) {
        if (!indent.equals(""))
            builder.append(indent)
        for (i in children.indices) {
            children[i].buildString(logLevel, time, message, tag, exception, builder, indent)
        }
        builder.appendln()
    }
}

abstract class IndentScope : Scope() {
    override fun buildString(logLevel: LogLevel, time: Long, message: String, tag: String, exception: Exception?, builder: StringBuilder, indent: String) {
        for (i in children.indices) {
            children[i].buildString(logLevel, time, message, tag, exception, builder, indent + "   ")
        }
    }
}
