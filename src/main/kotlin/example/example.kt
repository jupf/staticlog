package example

import de.jupf.staticlog.Log


/**
 * Created on 20.12.2015.
 *
 * @author jpf
 */
fun main(args : Array<String>) {
    Log.format {
        line(date("yyyy-MM-dd HH:mm:ss"),space,level,space(2),message,space(2),tag)
//        indent {
//            line(message)
//        }
    }

    Log.info("This is an info message")
    Thread.sleep(50)
    Log.debug("This is a debug message")
    Thread.sleep(50)
    Log.warn("This is a warning message")
    Thread.sleep(50)
    Log.error("This is an error message")
    Thread.sleep(50)
    Log.error("This is an error message with an additional Exception for output", Exception("Exeption Text",Exception("This is the cause")))
}