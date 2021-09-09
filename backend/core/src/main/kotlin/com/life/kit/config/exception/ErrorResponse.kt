package com.life.kit.config.exception

import java.time.LocalDateTime

class ErrorResponse (
  val timestamp: LocalDateTime,
  val path: String,
  val status: Int = 0,
  val type: String,
  val message: String,
  val errors: List<InnerError>? = null
)
