package claiborne.firstapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FirstAPIApplication

fun main(args: Array<String>) {
	runApplication<FirstAPIApplication>(*args)
}
