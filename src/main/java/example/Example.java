package example;

import de.jupf.staticlog.Log;
import de.jupf.staticlog.Logger;
import de.jupf.staticlog.format.LogFormat;
import de.jupf.staticlog.core.LogLevel;

import static de.jupf.staticlog.Log.FormatOperations.*;

/**
 * @author J.Pfeifer
 * @created 21.12.2015
 */
public class Example {
    public static void main(String[] args) {
        LogFormat format = Log.newFormat();
        format.line(date("yyyy-MM-dd HH:mm:ss.SSS"), space(1), level(), text("/"), tag(), space(2), message(), space(2), occurrence());

        Exception exception = new Exception("Exception text", new Exception("This is the cause"));


        Log.info("This is an info message");
        Log.debug("This is a debug message");
        Log.warn("This is a warning message", "WithACustomTag");
        Log.error("This is an error message with an additional Exception for output", "AndACustomText", exception);

        Log.setLogLevel(LogLevel.WARN);
        Log.info("This message will not be shown");


        // multi-line format:
        format = Log.newFormat();
        format.line(date("yyyy-MM-dd HH:mm:ss.SSS"), space(1), level(), text("/"), tag(), space(2), occurrence());
        format.indent(line(message()));

        Log.error("This is a multi-line log message");

        Logger logger = Log.javaInstance();
        logger.debug("This message is from an individual logger instance");
    }
}
