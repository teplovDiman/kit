package com.life.kit.controller

import com.life.kit.config.KitConfigurationProperties
import com.life.kit.dto.InfoDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class UtilController(
        val properties: KitConfigurationProperties
) {

    @GetMapping("/api/info")
    fun blog(): InfoDto {
        return InfoDto(
                "Kit Application",
                properties.version!!,
                properties.gitHash!!,
                LocalDateTime.now().toString())
    }
}
