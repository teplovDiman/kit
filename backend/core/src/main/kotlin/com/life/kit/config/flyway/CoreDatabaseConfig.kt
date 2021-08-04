package com.life.kit.config.flyway

import com.life.kit.common.FlywayOrder
import org.springframework.stereotype.Component

@Component
open class CoreDatabaseConfig : DatabaseConfig {
  override val schema: String
    get() = SCHEMA_NAME
  override val location: String
    get() = "db/migration/core"
  override val order: FlywayOrder
    get() = FlywayOrder.CORE

  companion object {
    const val SCHEMA_NAME = "core"
  }
}
