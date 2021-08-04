package com.life.kit.modules.user_role_permission.role

import com.fasterxml.jackson.annotation.JsonIgnore
import com.life.kit.common.BaseEntity
import com.life.kit.config.UserDatabaseConfig
import com.life.kit.modules.user_role_permission.permission.PermissionEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.EAGER
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(schema = UserDatabaseConfig.SCHEMA_NAME, name = "role")
class RoleEntity(

  @Column(name = "name", nullable = false, unique = true)
  var name: String? = null,

  @ManyToMany(fetch = EAGER)
  @JoinTable(
    schema = "users",
    name = "permission_to_role",
    joinColumns = [JoinColumn(name = "role_id")],
    inverseJoinColumns = [JoinColumn(name = "permission_id")])
  @JsonIgnore
  var permissions: Set<PermissionEntity>? = null

) : BaseEntity() {

  override fun toString(): String {
    return "${this.javaClass.simpleName}(${super.toString()}, name=$name, permissions=$permissions)"
  }

}
