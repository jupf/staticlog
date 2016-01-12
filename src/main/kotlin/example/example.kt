package example

import de.jupf.staticlog.Log
import de.jupf.staticlog.core.LogLevel


/**
 * Created on 20.12.2015.
 *
 * @author jpf
 */
fun main(args : Array<String>) {
//    Log.format {
//        line(date("yyyy-MM-dd HH:mm:ss"),space,level,space,tag)
//        indent {
//            line(message)
//        }
//    }



    val exception = Exception("Exeption Text",Exception("This is the cause"));

    Log.info("This is an info message")
    Thread.sleep(50)
    Log.debug("This is a debug message with an additional Exception for output",exception)
    Thread.sleep(50)
    Log.warn("This is a warning message","with a custom tag")
    Thread.sleep(50)
    Log.error("This is an error message with an additional Exception for output", "and a custom tag", exception )

    Log.logLevel = LogLevel.INFO

    Log.error("This message will not be shown")
}