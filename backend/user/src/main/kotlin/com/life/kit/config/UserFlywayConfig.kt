package com.life.kit.config

import com.life.kit.config.flyway.FlywayConfig
import org.springframework.context.annotation.Configuration

@Configuration
open class UserFlywayConfig : FlywayConfig {
    override val schema: String
        get() = "users"
    override val location: String
        get() = "db/migration/user"
}
