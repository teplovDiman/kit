package com.life.kit.config.flyway

import com.life.kit.common.FlywayOrder

interface FlywayConfig {
  val schema: String
  val location: String
  val order: FlywayOrder
}
