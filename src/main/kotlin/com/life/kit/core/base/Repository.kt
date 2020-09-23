package com.life.kit.core.base

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface Repository<T : BaseEntity?> : EntityGraphJpaRepository<T, Long?> 