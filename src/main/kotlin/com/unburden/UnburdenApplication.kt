package com.unburden

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UnburdenApplication

fun main(args: Array<String>) {
	runApplication<UnburdenApplication>(*args)
}
