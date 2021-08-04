package com.life.kit.config

import com.life.kit.config.flyway.DatabaseConfig
import com.life.kit.common.FlywayOrder
import org.springframework.stereotype.Component

@Component
open class UserDatabaseConfig : DatabaseConfig {
  override val schema: String
    get() = SCHEMA_NAME
  override val location: String
    get() = "db/migration/user"
  override val order: FlywayOrder
    get() = FlywayOrder.USER

  companion object {
    const val SCHEMA_NAME = "users"
  }
}
