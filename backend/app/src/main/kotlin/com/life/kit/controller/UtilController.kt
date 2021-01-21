package com.life.kit.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UtilController {

    @GetMapping("/api/version")
    fun blog(): String {
        return "0.0.1"
    }
}
