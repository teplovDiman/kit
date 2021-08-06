package com.life.kit.common

import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface BaseMapper<Entity : BaseEntity, Dto : BaseDto> {

  fun dtoToEntity(dto: Dto?): Entity

  fun entityToDto(entity: Entity?): Dto

  @Mapping(target = "id", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  fun updateEntityFromDto(@MappingTarget entity: Entity, dto: Dto)

}
