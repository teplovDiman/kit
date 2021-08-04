package com.life.kit.config.flyway

import com.life.kit.common.FlywayOrder
import org.springframework.stereotype.Component

@Component
open class CoreFlywayConfig : FlywayConfig {
  override val schema: String
    get() = "core"
  override val location: String
    get() = "db/migration/core"
  override val order: FlywayOrder
    get() = FlywayOrder.CORE
}
