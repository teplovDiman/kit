package com.life.kit.common

import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class KitHelper {

  fun getLocalDateTimeNow(): LocalDateTime = LocalDateTime.now()
}
