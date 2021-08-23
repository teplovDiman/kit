package com.life.kit.modules.user_role_permission.common

import com.life.kit.common.BaseEmptyDto
import com.life.kit.common.BaseEntity
import com.life.kit.common.IdDto
import com.life.kit.common.KitHelper
import com.life.kit.modules.user_role_permission.user.UserHelper
import org.mapstruct.AfterMapping
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.mapstruct.Named
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy
import org.springframework.beans.factory.annotation.Autowired

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
abstract class BaseMapper<Entity : BaseEntity, Dto : BaseEmptyDto, DtoWithId : IdDto> {

  @Autowired
  private lateinit var userHelper: UserHelper

  @Autowired
  private lateinit var kitHelper: KitHelper

  @BeanMapping(qualifiedByName = ["setAuditableFields"])
  abstract fun dtoToEntity(dto: Dto?): Entity

  abstract fun entityToDto(entity: Entity?): DtoWithId

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  abstract fun updateEntityFromDto(@MappingTarget entity: Entity, dto: Dto)

  @Named("setAuditableFields")
  @AfterMapping
  fun setAuditableFields(@MappingTarget entity: Entity): Entity {
    if (entity is AuditableEntity) {
      entity.createdBy = userHelper.getCurrentUser()
      entity.createdAt = kitHelper.getLocalDateTimeNow()
    }
    return entity
  }
}
