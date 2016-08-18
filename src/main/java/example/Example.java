package example;

import de.jupf.staticlog.Log;
import de.jupf.staticlog.core.LogLevel;
import de.jupf.staticlog.core.Logger;
import de.jupf.staticlog.format.LogFormat;

import static de.jupf.staticlog.Log.FormatOperations.*;


/**
 * Created on 21.12.2015
 *
 * @author J.Pfeifer
 */
public class Example {
    public static void main(String[] args) {
        try {
            LogFormat format = Log.newFormat();
            format.line(date("yyyy-MM-dd HH:mm:ss.SSS"), space(1), level(), text("/"), tag(), space(2), message(), space(2), occurrence());

            Exception exception = new Exception("Exception text", new Exception("This is the cause"));

            Log.info("This is an info message");
            Thread.sleep(50);
            Log.debug("This is a debug message");
            Thread.sleep(50);
            Log.warn("This is a warning message", "WithACustomTag");
            Thread.sleep(50);
            Log.error("This is an error message with an additional Exception for output", "AndACustomText", exception);
            Thread.sleep(50);

            Log.setLogLevel(LogLevel.WARN);
            Log.info("This message will not be shown");
            Thread.sleep(50);

            Log.setLogLevel(LogLevel.INFO);

            Log.setTagFilter("filterTag");
            Log.info("This log will be filtered out", "otherTag");
            Thread.sleep(50);
            Log.info("This log has the right tag", "filterTag");
            Thread.sleep(50);
            Log.deleteTagFilter();

            // multi-line format:
            format = Log.newFormat();
            format.line(date("yyyy-MM-dd HH:mm:ss.SSS"), space(1), level(), text("/"), tag(), space(2), occurrence());
            format.indent(line(message()));

            Log.error("This is a multi-line log message");
            Thread.sleep(50);

            Logger logger = Log.javaInstance();
            logger.newFormat().line(message());
            logger.debug("This message is from an individual logger instance");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
