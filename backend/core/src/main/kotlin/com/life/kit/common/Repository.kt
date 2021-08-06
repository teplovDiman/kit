package com.life.kit.common

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface Repository<T : BaseEntity> : JpaRepository<T, Long?>

fun <T, ID> JpaRepository<T, ID>.getOneById(id: ID): T = getById(id)
