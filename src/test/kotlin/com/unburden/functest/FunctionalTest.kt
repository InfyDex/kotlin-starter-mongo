package com.unburden.functest

import io.cucumber.junit.Cucumber
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest
@RunWith(Cucumber::class)
class FunctionalTest

