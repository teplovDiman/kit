package com.life.kit.controller

import com.life.kit.common.KitHelper
import com.life.kit.config.KitPropertiesConfiguration
import com.life.kit.dto.InfoDto
import com.life.kit.dto.InfoStackTechnologyDto
import org.springframework.boot.SpringBootVersion
import org.springframework.core.SpringVersion
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UtilController(

  private val kitProperties: KitPropertiesConfiguration,
  private val kitHelper: KitHelper

) {

  @GetMapping("/api/info")
  fun getInfo(): InfoDto {
    // TODO: add Postgres, Gradle versions

    val stackTechnology = InfoStackTechnologyDto(
      System.getProperty("java.version"),
      SpringVersion.getVersion()!!,
      SpringBootVersion.getVersion())

    return InfoDto(
      "Kit Application",
      kitProperties.version,
      kitProperties.gitHash,
      kitHelper.getLocalDateTimeNow().toString(),
      stackTechnology
    )
  }
}
