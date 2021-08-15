package com.life.kit.modules.note

import com.life.kit.common.BaseDto
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

open class NoteDto (

  @get:NotBlank
  @get:Size(min = 1, max = 255)
  var title: String? = null,

  @get:NotBlank
  var value: String? = null,

): BaseDto()
