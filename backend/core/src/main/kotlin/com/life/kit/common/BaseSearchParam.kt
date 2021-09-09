package com.life.kit.common

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.domain.Sort.Direction.ASC
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Schema(description = "Supported parameters for retrieve objects")
data class BaseSearchParam(

  @field:Parameter(description = "Zero-based page index",schema = Schema(implementation = Int::class, minimum = "0", defaultValue = "0"))
  @Min(0)
  val page: Int = 0,

  @field:Parameter(description = "The size of the page to be returned",
    schema = Schema(implementation = Int::class, minimum = "5", maximum = "100", defaultValue = "10"))
  @Min(5)
  @Max(100)
  val size: Int = 10,

  @field:Parameter(description = "The size of the page to be returned", schema = Schema(implementation = Direction::class, defaultValue = "ASC"))
  val sortDir: Direction = ASC

)
