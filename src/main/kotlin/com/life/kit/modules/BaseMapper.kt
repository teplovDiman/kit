package com.life.kit.modules

interface BaseMapper<E : BaseEntity?, D, T> {

    fun entityToDto(entity: E): T
    fun dtoToEntity(dto: D): E
}
