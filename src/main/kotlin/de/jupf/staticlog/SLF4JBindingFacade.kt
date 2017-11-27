package de.jupf.staticlog

import de.jupf.staticlog.core.LogLevel
import de.jupf.staticlog.core.Logger

/**
 * This is the facade used by the SLF4J binding to create logger instances.
 *
 * @created 19.08.2016
 * @author jpf
 */
object SLF4JBindingFacade {
    @JvmStatic @Synchronized
    fun createSLF4JLoggerInstance(name: String): Logger {
        val logger = Logger(5)
        logger.logLevel = logLevel
        (Log.slf4jLogInstancesMap as MutableMap).put(name, logger)
        return logger
    }

    internal var logLevel: LogLevel = LogLevel.DEBUG
        @Synchronized
        set(value) {
            field = value
            Log.slf4jLogInstancesMap.values.forEach {
                it.logLevel = value
            }
        }
}