@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.life.kit.controller

import com.life.kit.config.KitConfigurationProperties
import com.life.kit.dto.InfoDto
import com.life.kit.dto.InfoStackTechnologyDto
import org.springframework.boot.SpringBootVersion
import org.springframework.core.SpringVersion
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class UtilController(
  val properties: KitConfigurationProperties
) {

  @GetMapping("/api/info")
  fun getInfo(): InfoDto {
    // TODO: add Postgres, Gradle versions

    val stackTechnology = InfoStackTechnologyDto(
      System.getProperty("java.version"),
      SpringVersion.getVersion(),
      SpringBootVersion.getVersion())

    return InfoDto(
      "Kit Application",
      properties.version!!,
      properties.gitHash!!,
      LocalDateTime.now().toString(),
      stackTechnology
    )
  }
}
