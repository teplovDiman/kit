// region Suppresses
@file:Suppress("unused")
// endregion

package com.life.kit.dto

class InfoDto(
  val instance: String,
  val version: String,
  val gitHash: String,
  val localDateTime: String,
  val stackTechnology: InfoStackTechnologyDto
)
