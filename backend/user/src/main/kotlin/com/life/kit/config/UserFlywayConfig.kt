package com.life.kit.config

import com.life.kit.config.flyway.FlywayConfig
import com.life.kit.common.FlywayOrder
import org.springframework.stereotype.Component

@Component
open class UserFlywayConfig : FlywayConfig {
  override val schema: String
    get() = "users"
  override val location: String
    get() = "db/migration/user"
  override val order: FlywayOrder
    get() = FlywayOrder.USER
}
