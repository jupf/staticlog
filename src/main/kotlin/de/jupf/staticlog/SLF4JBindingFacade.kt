package de.jupf.staticlog

import de.jupf.staticlog.core.Logger

/**
 * INSERT DESCRIPTION
 *
 * @created 19.08.2016
 * @author jpf
 */
object SLF4JBindingFacade {
    @JvmStatic
    fun addSLF4JLogger(name: String, instance: Logger) {
        (Log.slf4jLogInstancesMap as MutableMap).put(name, instance)
    }

    @JvmStatic
    fun createSLF4JLoggerInstance() = Logger(5)
}