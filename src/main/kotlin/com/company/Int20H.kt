package com.company

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
open class Int20H

fun main(args: Array<String>) {
    runApplication<Int20H>(*args)
}
