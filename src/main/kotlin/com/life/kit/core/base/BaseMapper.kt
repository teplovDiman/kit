package com.life.kit.core.base

interface BaseMapper<E : BaseEntity?, D> {

    fun entityToDto(entity: E): D
    fun dtoToEntity(dto: D): E
}