package com.life.kit.modules

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface Repository<T : BaseEntity?> : EntityGraphJpaRepository<T, Long?>