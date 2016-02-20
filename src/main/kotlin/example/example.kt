package example

import de.jupf.staticlog.Log
//import de.jupf.staticlog.
import de.jupf.staticlog.core.LogLevel

/**
 * @created 20.12.2015
 * @author J.Pfeifer
 */
fun main(args: Array<String>) {
    Log.newFormat {
        line(date("yyyy-MM-dd HH:mm:ss.SSS"), space, level, text("/"), tag, space(2), message, space(2), occurrence)
    }

    val exception = Exception("Exeption Text", Exception("This is the cause"));

    Log.info("This is an info message")
    Thread.sleep(50)
    Log.debug("This is a debug message")
    Thread.sleep(50)
    Log.warn("This is a warning message", "WithACustomTag")
    Thread.sleep(50)
    Log.error("This is an error message with an additional Exception for output", "AndACustomTag", exception)
    Thread.sleep(50)

    Log.logLevel = LogLevel.WARN

    Log.info("This message will not be shown")
    Thread.sleep(50)

    // multi-line format:
    Log.newFormat {
        line(date("yyyy-MM-dd HH:mm:ss.SSS"), space, level, text("/"), tag, space(2), occurrence)
        indent {
            line(message)
        }
    }

    Log.error("This is a multi-line log message")
    Thread.sleep(50)

    val logger = Log.kotlinInstance()
    logger.debug("This message is from an individual logger instance")
}