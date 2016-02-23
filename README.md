# StaticLog [![Kotlin](https://img.shields.io/badge/Kotlin-1.0.0-blue.svg) ](https://kotlinlang.org/)  [![license](https://img.shields.io/badge/license-MIT-blue.svg) ](https://github.com/jupf/staticlog/blob/master/LICENSE) [![Dependency Status](https://www.versioneye.com/user/projects/56cc29b518b2710403dfed86/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56cc29b518b2710403dfed86)  
StaticLog is a super lightweight logging library implemented in pure Kotlin ([https://kotlinlang.org](https://kotlinlang.org/)). It is designed to be used in Kotlin, Java and Android.  
It is for formatting standard output comfortably without the need to construct a Logger object. But it's also no problem to create one.  
  
This is an example output in [IntelliJ IDEA](http://www.jetbrains.com/idea/)
![example output](https://drive.google.com/uc?export=view&id=0B3Hm3TAXNccQVnRNODRDWDg2eFk)
Yes, the occurrence of the log message is clickable!  

## Table of Contents
- [StaticLog](#staticlog)
	- [StaticLog in Kotlin](#staticlog-in-kotlin)
		- [Logging in Kotlin](#logging-in-kotlin)
		- [Formatting Output in Kotlin](#formatting-output-in-kotlin)
        - [Tag Filtering in Kotlin](#tag-filtering-in-kotlin)
		- [Log instances in Kotlin](#log-instances-in-kotlin)
		- [FormatBuilders in Kotlin](#formatbuilders-in-kotlin)
	- [StaticLog in Java](#staticlog-in-java)
		- [Logging in Java](#logging-in-java)
		- [Formatting Output in Java](#formatting-output-in-java)
		- [Tag Filtering in Java](#tag-filtering-in-java)
		- [Log instances in Java](#log-instances-in-java)
		- [FormatBuilders in Java](#formatbuilders-in-java)
	- [StaticLog in Android](#staticlog-in-android)

## StaticLog in Kotlin
You can find the example source code [here](https://github.com/jupf/staticlog/blob/master/src/main/kotlin/example/example.kt).  
When the Kotlin Runtime is already present you should use the [staticlog-2.1.1.jar](https://github.com/jupf/staticlog/releases/download/v2.1.1/staticlog-2.1.1.jar).  

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
Log.newFormat {
    line(date("yyyy-MM-dd HH:mm:ss"), space, level, text("/"), tag, space(2), message, space(2), occurrence)
}
```

You can even define multiple lines and indent them:  

```kotlin
Log.newFormat {
    line(date("yyyy-MM-dd HH:mm:ss"), space, level, text("/"), tag, space(2), occurrence)
    indent {
        line(message)
    }
}
```

### Tag Filtering in Kotlin  

It is possible to filter the output for a specific tag. This is rather easy, just provide the tag:  

```kotlin
Log.filterTag = "filterTag"
Log.info("This log will be filtered out", "otherTag")
Log.info("This log has the right tag", "filterTag")
```

Deleting a tag filter is just as easy:  

```kotlin
Log.deleteTagFilter()
```

### Log instances in Kotlin  

If you need another log instance you can create one very easy. It can have an own format and log level:
```kotlin
val logger = Log.kotlinInstance()
logger.debug("This message is from an individual logger instance")
```

The interface of individual log instances is exactly the same as the interface for the static Log class.  

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
When the Kotlin Runtime is __not__ already present you should use the  [staticlog-java-2.1.1.jar](https://github.com/jupf/staticlog/releases/download/v2.1.1/staticlog-java-2.1.1.jar).  

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
import static de.jupf.staticlog.Log.FormatOperations.*;

...

LogFormat format = Log.newFormat();
format.line(date("yyyy-MM-dd HH:mm:ss"), space(1), level(), text("/"), tag(), space(2), message(), space(2), occurrence());
```

You can even define multiple lines and indent them:  

```java
LogFormat format = Log.newFormat();
format.line(date("yyyy-MM-dd HH:mm:ss"), space(1), level(), text("/"), tag(), space(2), occurrence());
format.indent(line(message()));
```

### Tag Filtering in Java  

It is possible to filter the output for a specific tag. This is rather easy, just provide the tag:  

```java
Log.setTagFilter("filterTag");
Log.info("This log will be filtered out", "otherTag");
Log.info("This log has the right tag", "filterTag");
```

Deleting a tag filter is just as easy:  

```java
Log.deleteTagFilter();
```

### Log instances in Java  

If you need another log instance you can create one very easy. It can have an own log level and format:  

```Java
Logger logger = Log.javaInstance();
logger.debug("This message is from an individual logger instance");
```

The interface of individual log instances is exactly the same as the interface for the static Log class.  

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
format.line(message(), space(2), occurrence());
```

The tag is forwarded to the Android logger. If none is provided, it defaults to the class name the log was printed from.  
For further questions look at [StaticLog in Java](#staticlog-in-java)
