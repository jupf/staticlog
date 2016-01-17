# StaticLog
StaticLog is a super lightweight logging library written in pure Kotlin. It is designed to be used in Kotlin, Java and Android.  
It is for formatting standard output comfortably without the need to construct a Logger object. 

## Table of Contents

- [StaticLog](#staticlog)
	- [StaticLog in Kotlin](#staticlog-in-kotlin)
		- [Logging in Kotlin](#logging-in-kotlin)
		- [Formatting Output in Kotlin](#formatting-output-in-kotlin)
		- [FormatBuilders in Kotlin](#formatbuilders-in-kotlin)
	- [StaticLog in Java](#staticlog-in-java)
		- [Logging in Java](#logging-in-java)
		- [Formatting Output in Java](#formatting-output-in-java)
		- [FormatBuilders in Java](#formatbuilders-in-java)
	- [StaticLog in Android](#staticlog-in-android)

## StaticLog in Kotlin
You can find the example source code [here](https://github.com/jupf/staticlog/blob/master/src/main/kotlin/example/example.kt).
### Logging in Kotlin
Logging with StaticLog in Kotlin is as easy as it gets:
```kotlin
Log.info("This is an info message")
Log.debug("This is a debug message")
Log.warn("This is a warning message","WithACustomTag")
Log.error("This is an error message with an additional Exception for output", "AndACustomTag", exception )

Log.logLevel = LogLevel.WARN
Log.info("This message will not be shown")
```
### Formatting Output in Kotlin
To define an output format for StaticLog in Kotlin is very easy. It uses a mix from builders and function call syntax.  
This defines for example the default format:  
```kotlin
Log.format {
    line(date("yyyy-MM-dd HH:mm:ss"), space, level, text("/"), tag, space(2), message, space(2), occurrence)
}
```
You can even define multiple lines and indent them:  
```kotlin
Log.format {
    line(date("yyyy-MM-dd HH:mm:ss"), space, level, text("/"), tag, space(2), occurrence)
    indent {
        line(message)
    }
}
```
### FormatBuilders in Kotlin
Here are all possible FormatBuilders: 

| FormatBuilder   |     Output      |
|:----------------:|:---------------:|
| date(Pattern)    | Prints the date in the given pattern  <br>(The pattern is defined in [SimpleDateFormat](http://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html))</br>  |
| epoch            | Prints the current time in milliseconds  |
| level            | Prints the log level |
| tag              | Prints the log tag <br>(If none was provided to the logging function, <br>it defaults to the class name the message was logged from.)</br></br> |
| message          | Prints the log message |
| occurrence       | Prints the origin of the logging (In Eclipse and IntelliJ clickable!)|
| space            | Prints 1 space  |
| space(X: Integer)| Prints X spaces    |
| tab              | Prints 1 tab |
| tab(X: Integer)  | Prints X tabs |
| text(S: String)  | Prints the String S |

## StaticLog in Java
You can find the example source code [here](https://github.com/jupf/staticlog/blob/master/src/main/java/example/Example.java).
### Logging in Java
Logging with StaticLog in Kotlin is as easy as it gets:
```java
Log.info("This is an info message");
Log.debug("This is a debug message");
Log.warn("This is a warning message","WithACustomTag");
Log.error("This is an error message with an additional Exception for output", "AndACustomTag", exception );

Log.setLogLevel(LogLevel.WARN);
Log.info("This message will not be shown");
```
### Formatting Output in Java
To define an output format for StaticLog in Java is very easy.  
This defines for example the default format:  
```java
import static de.jupf.staticlog.Log.format.*;

...

Format format = Log.format.create();
format.line(date("yyyy-MM-dd HH:mm:ss"), space(1), level(), text("/"), tag(), space(2), message(), space(2), occurrence());
```
You can even define multiple lines and indent them:  
```java
format = Log.format.create();
format.line(date("yyyy-MM-dd HH:mm:ss"), space(1), level(), text("/"), tag(), space(2), occurrence());
format.indent(line(message()));
```
### FormatBuilders in Java
Here are all possible FormatBuilders:

| FormatBuilder   |     Output      |
|:----------------:|:---------------:|
| date(Pattern)    | Prints the date in the given pattern  <br>(The pattern is defined in [SimpleDateFormat](http://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html))</br>  |
| epoch()          | Prints the current time in milliseconds  |
| level()          | Prints the log level |
| tag()            | Prints the log tag <br>(If none was provided to the logging function, <br>it defaults to the class name the message was logged from.)</br></br> |
| message()        | Prints the log message |
| occurrence()     | Prints the origin of the logging (In Eclipse and IntelliJ clickable!)|
| space(X: Integer)| Prints X spaces    |
| tab(X: Integer)  | Prints X tabs |
| text(S: String)  | Prints the String S |

## StaticLog in Android
StaticLog automatically detects Android VMs and switches its output to the Android logger.  
The default format for Android is defined like this:  
```java
Format format = Log.format.create();
format.line(message(), space(2), occurrence());
```
The tag is forwarded to the Android logger. If none is provided, it defaults to the class name the log was printed from.  
For further questions look at [StaticLog in Java](#staticlog-in-java)
