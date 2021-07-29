package com.life.kit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KitApplication

fun main(args: Array<String>) {

  runApplication<KitApplication>(*args)
}
