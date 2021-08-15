package com.life.kit.config

import com.life.kit.common.FlywayOrder
import com.life.kit.config.flyway.DatabaseConfig
import org.springframework.stereotype.Component

@Component
open class NoteDatabaseConfig : DatabaseConfig {
  override val schema: String
    get() = SCHEMA_NAME
  override val location: String
    get() = "db/migration/note"
  override val order: FlywayOrder
    get() = FlywayOrder.NOTE

  companion object {
    const val SCHEMA_NAME = "note"
  }
}
