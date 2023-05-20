package com.company.autocontrol

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AutoControlServerApplication

fun main(args: Array<String>) {
    runApplication<AutoControlServerApplication>(args = args)
}
