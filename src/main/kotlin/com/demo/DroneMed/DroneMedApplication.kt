package com.demo.DroneMed

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class DroneMedApplication

fun main(args: Array<String>) {
	runApplication<DroneMedApplication>(*args)
}
