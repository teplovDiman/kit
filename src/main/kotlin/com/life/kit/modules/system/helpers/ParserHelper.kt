package com.life.kit.modules.system.helpers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.life.kit.modules.system.json.ExtraJson
import org.springframework.stereotype.Component

@Component
class ParserHelper {

    companion object {

        private val objectMapper = ObjectMapper()

        fun getSetOfStringFromJsonOrEmpty(json: ExtraJson?): Set<String> {
            if (json == null) {
                return HashSet()
            }
            try {
                val value = json[ExtraJson.DEFAULT_PARAM_NAME].toString()
                return objectMapper.readValue(value)
            } catch (ignored: Exception) {
            }
            return HashSet()
        }

        fun getStringFromCollectionOrNull(values: Collection<*>?): String? {
            try {
                return objectMapper.writeValueAsString(values)
            } catch (ignored: Exception) {
            }
            return null
        }
    }
}