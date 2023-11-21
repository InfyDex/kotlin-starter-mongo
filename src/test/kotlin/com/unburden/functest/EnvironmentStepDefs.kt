package com.unburden.functest

import io.cucumber.java.Before

class EnvironmentStepDefs(private val environment: LocalSpringBootEnvironment) {
    @Before
    fun start() {
        environment.start()
    }
}