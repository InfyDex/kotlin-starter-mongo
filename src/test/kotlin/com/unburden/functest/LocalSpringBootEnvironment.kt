package com.unburden.functest

import com.unburden.UnburdenApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ApplicationContext

class LocalSpringBootEnvironment {
    private var instance: ApplicationContext? = null

    internal object SpringHolder {
        var INSTANCE = instance()
        private fun instance(): ApplicationContext {
            return SpringApplicationBuilder(UnburdenApplication::class.java)
                .profiles("test")
                .build()
                .run()
        }
    }

    private var port = 0

    fun start() {
        instance = SpringHolder.INSTANCE
        port = instance!!.environment.getProperty("local.server.port", Int::class.java)!!
    }
}