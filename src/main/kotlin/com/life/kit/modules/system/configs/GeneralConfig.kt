package com.life.kit.modules.system.configs

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["com.life"], repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean::class)
class GeneralConfig