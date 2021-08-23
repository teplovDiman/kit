package com.life.kit.common

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface BaseRepository<T : BaseEntity> : JpaRepository<T, Long?> {

  fun findAllByCreatedById(id: Long, pageable: Pageable): Page<T>
}

fun <T, ID> JpaRepository<T, ID>.getOneById(id: ID): T = getById(id)
