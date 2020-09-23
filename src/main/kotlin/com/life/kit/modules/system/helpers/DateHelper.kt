package com.life.kit.modules.system.helpers

import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Component
class DateHelper {

    companion object {
        val DATE_FORMAT = "yyyy-MM-dd"
    }

    fun localDateTimeNow(): LocalDateTime {
        return LocalDateTime.now()
    }

    fun localDateNow(): LocalDate {
        return LocalDate.now()
    }

    fun localTimeNow(): LocalTime {
        return LocalTime.now()
    }
}
