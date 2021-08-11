package com.life.kit.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
data class KitPropertiesConfiguration(
    var version: String = "",
    var gitHash: String = ""
)
