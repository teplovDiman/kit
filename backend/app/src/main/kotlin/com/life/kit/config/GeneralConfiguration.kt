package com.life.kit.config

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["com.life"], repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean::class)
@EnableConfigurationProperties(KitPropertiesConfiguration::class)
class GeneralConfiguration