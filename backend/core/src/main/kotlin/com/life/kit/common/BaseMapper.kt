package com.life.kit.common

import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface BaseMapper<Entity : BaseEntity, Dto : BaseDto, DtoWithId : IdDto> {

  fun dtoToEntity(dto: Dto?): Entity

  fun entityToDto(entity: Entity?): DtoWithId

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  fun updateEntityFromDto(@MappingTarget entity: Entity, dto: Dto)

}
