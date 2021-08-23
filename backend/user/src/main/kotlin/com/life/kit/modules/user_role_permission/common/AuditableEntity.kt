package com.life.kit.modules.user_role_permission.common

import com.life.kit.modules.user_role_permission.user.UserEntity
import java.time.LocalDateTime

interface AuditableEntity {

  var createdBy: UserEntity?
  var createdAt: LocalDateTime?

}
