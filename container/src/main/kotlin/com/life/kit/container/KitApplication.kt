package com.life.kit.container

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KitApplication

fun main(args: Array<String>) {

    runApplication<KitApplication>(*args)
}
