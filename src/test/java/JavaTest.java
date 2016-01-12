import de.jupf.staticlog.Log;
import de.jupf.staticlog.core.LogLevel;
import de.jupf.staticlog.format.Format;

import static de.jupf.staticlog.Log.format.*;

/**
 * Created on 21.12.2015.
 *
 * @author J.Pfeifer
 */
public class JavaTest {
    public static void main(String[] args) {
        Format format = Log.format.create();
        format.line(date("yyyy-MM-dd HH:mm:ss"), space(1), level(), space(1), tag());
        format.indent(line(message()));

        // Log.info();

        Log.info("This is an info message");
        Log.debug("This is a debug message");
        Log.warn("This is a warning message");
        Log.error("This is a error message");
        Log.error("This is an error message with an additional Exception for output",
                new Exception("Exeption Text",new Exception("This is the cause")));

        Log.setLogLevel(LogLevel.INFO);
        Log.error("This message will not be shown");
    }
}
