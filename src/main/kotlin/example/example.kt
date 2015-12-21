package example

import de.jupf.staticlog.Log


/**
 * Created on 20.12.2015.
 *
 * @author jpf
 */
fun main(args : Array<String>) {
    Log.format {
        line(date("yyyy-MM-dd HH:mm:ss"),space(2),level,space,tag)
        indent {
            line(message)
        }
    }

    Log.info("This is an info message")
    Thread.sleep(50)
    Log.debug("This is a debug message")
    Thread.sleep(50)
    Log.warn("This is a warning message")
    Thread.sleep(50)
    Log.error("This is a error message")

}