package example;

import de.jupf.staticlog.Log;
import de.jupf.staticlog.core.LogLevel;
import de.jupf.staticlog.format.Format;

import static de.jupf.staticlog.Log.format.*;

/**
 * Created on 21.12.2015.
 *
 * @author J.Pfeifer
 */
public class Example {
    public static void main(String[] args) {
        Format format = Log.format.create();
        format.line(date("yyyy-MM-dd HH:mm:ss"), space(1), level(), tab(1), tag(), space(2), message(), space(2), occurrence());

        Exception exception = new Exception("Exception text", new Exception("This is the cause"));

        Log.info("This is an info message");
        Log.debug("This is a debug message");
        Log.warn("This is a warning message","with a custom tag");
        Log.error("This is an error message with an additional Exception for output", "and a custom tag", exception );

        Log.setLogLevel(LogLevel.WARN);
        Log.info("This message will not be shown");



        // multi-line format:
        format = Log.format.create();
        format.line(date("yyyy-MM-dd HH:mm:ss"), space(1), level(), tab(1), tag(), space(2), occurrence());
        format.indent(line(message()));

        Log.error("This is a multi-line log message");
    }
}
