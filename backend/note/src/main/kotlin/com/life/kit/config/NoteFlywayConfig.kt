package com.life.kit.config

import com.life.kit.config.flyway.FlywayConfig
import com.life.kit.common.FlywayOrder
import org.springframework.stereotype.Component

@Component
open class NoteFlywayConfig : FlywayConfig {
  override val schema: String
    get() = "note"
  override val location: String
    get() = "db/migration/note"
  override val order: FlywayOrder
    get() = FlywayOrder.NOTE
}
