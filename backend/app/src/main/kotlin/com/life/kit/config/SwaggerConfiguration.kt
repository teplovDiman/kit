package com.life.kit.config

import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration(

  private val kitProperties: KitPropertiesConfiguration

) {

  @Bean
  fun openAPIBean(): OpenAPI? {
    return OpenAPI()
      .info(
        Info()
          .title("REST API for Kit Application")
          .description("REST API for Kit Application offer you whole available API to create your custom client, " +
              "service for integration, bots or even... but let's start!")
          .version(kitProperties.version)
          .license(
            License()
              .name("GPL-3.0 License")
              .url("https://www.gnu.org/licenses/gpl-3.0.en.html")))
      .externalDocs(
        ExternalDocumentation()
          .description("Documentation")
          .url("https://github.com/teplovDiman/kit/wiki"))
  }
}
