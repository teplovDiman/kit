package com.life.kit.modules.user_role_permission.user

import org.springframework.stereotype.Component

@Component
class UserHelper{

  fun getCurrentUserId(): Long {
    return 1 // TODO: Mocked. Need to implement it
  }

  fun getCurrentUser(): UserEntity {
    val result = UserEntity()
    result.id = getCurrentUserId()
    return result // TODO: Mocked. Need to implement it
  }
}
