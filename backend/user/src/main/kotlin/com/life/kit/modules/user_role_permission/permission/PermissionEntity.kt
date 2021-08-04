package com.life.kit.modules.user_role_permission.permission

import com.life.kit.common.BaseEntity
import com.life.kit.config.UserDatabaseConfig
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(schema = UserDatabaseConfig.SCHEMA_NAME, name = "permission")
class PermissionEntity(

  @Column(name = "name", nullable = false, unique = true)
  var name: String? = null,

  @Column(name = "description", nullable = false)
  var description: String? = null

) : BaseEntity() {

  override fun toString(): String {
    return "${this.javaClass.simpleName}(${super.toString()}, name=$name, description=$description)"
  }
}
