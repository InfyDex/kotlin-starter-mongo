package com.unburden.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.event.Level

class ApplicationLogger(private val logger: Logger) {
    fun error(message: String) {
        if (logger.isErrorEnabled) {
            logger.error(message)
        }
    }

    fun warn(message: String) {
        if (logger.isWarnEnabled) {
            logger.warn(message)
        }
    }

    fun info(message: String) {
        if (logger.isInfoEnabled) {
            logger.info(message)
        }
    }

    fun debug(message: String) {
        if (logger.isDebugEnabled) {
            logger.debug(message)
        }
    }

    fun log(logLevel: Level?, logEvent: String) {
        when (logLevel) {
            Level.ERROR -> this.error(logEvent)
            Level.WARN -> warn(logEvent)
            Level.INFO -> info(logEvent)
            Level.DEBUG -> debug(logEvent)
            else -> info(logEvent)
        }
    }

    companion object {
        fun getLogger(clazz: Class<*>?): ApplicationLogger {
            return ApplicationLogger(LoggerFactory.getLogger(clazz))
        }

        fun getLogger(loggerName: String?): ApplicationLogger {
            return ApplicationLogger(LoggerFactory.getLogger(loggerName))
        }
    }
}