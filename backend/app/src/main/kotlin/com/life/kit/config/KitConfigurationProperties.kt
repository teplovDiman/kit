package com.life.kit.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
class KitConfigurationProperties {
    var version: String? = null
    var gitHash: String? = null
}