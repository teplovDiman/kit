package com.life.kit.common

// 'value' is presenting the order of dependencies between modules
enum class FlywayOrder(val value: Int) {
  CORE(0),
  USER(1),
  NOTE(2)
}
