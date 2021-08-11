package com.life.kit.config.flyway

import mu.KotlinLogging
import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration

private val log = KotlinLogging.logger {}

@Configuration
open class FlywayMigrationConfiguration(
  private val context: ApplicationContext,
  @Value("\${spring.datasource.url}") private val datasourceUrl: String,
  @Value("\${spring.datasource.username}") private val datasourceUsername: String,
  @Value("\${spring.datasource.password}") private val datasourcePassword: String
) : FlywayMigrationStrategy {

  override fun migrate(flyway: Flyway) {
    // todo: Why flyway.configuration.url, flyway.configuration.username and flyway.configuration.password
    // is empty? By this reason it require to get @Value property in constructor.
    // Is there is way to avoid this crutch?

    configs.forEach {
      startMigration(it.schema, it.location)
    }
  }

  private fun startMigration(schema: String, location: String) {
    log.info { "Flyway: Start migration for schema: $schema ..." }
    Flyway.configure()
      .dataSource(datasourceUrl, datasourceUsername, datasourcePassword)
      .schemas(schema)
      .table("flyway_version")
      .locations(location)
      .baselineOnMigrate(true)
      .load()
      .migrate()
  }

  private val configs: List<DatabaseConfig>
    get() = context.getBeansOfType(DatabaseConfig::class.java)
      .entries
      .map { x: Map.Entry<String?, DatabaseConfig> -> x.value }
      .sortedBy { it.order.ordinal }
}
