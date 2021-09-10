package com.life.kit.modules.userRolePermission.common

import com.life.kit.modules.userRolePermission.user.UserEntity
import java.time.LocalDateTime

interface AuditableEntity {

  var createdBy: UserEntity?
  var createdAt: LocalDateTime?

}
