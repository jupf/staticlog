# StaticLog
StaticLog is a super lightweight logging library written in pure Kotlin. It is designed to be used in Kotlin and Java.  
It is just for formatting standard output. There are no fancy extras added!  
Android support will be added in the future.  
## Getting Started in Kotlin
You can find the example source code [here](https://github.com/jupf/staticlog/blob/master/src/main/kotlin/example/example.kt).
### Formatting Output
To define an output format for StaticLog in Kotlin is very easy. It uses a mix from builders and function call syntax.  
For example:  
```Kotlin
Log.format {
    line(date("yyyy-MM-dd HH:mm:ss"),space,level,space(2),message,space(2),tag)
}
```
You can even define multiple lines and indent them. See [Formatting in Details] (https://github.com/jupf/staticlog#formatting-in-details)
### Logging in Kotlin
Logging with StaticLog in Kotlin is as easy as it gets:
```kotlin
Log.info("This is an info message")
Log.debug("This is a debug message with an additional Exception for output",exception)
Log.warn("This is a warning message","with a custom tag")
Log.error("This is an error message with an additional Exception for output", "and a custom tag", exception )
```
## Getting Started in Java
coming soon..
### Formatting in Details
coming soon..
