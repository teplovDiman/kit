package com.life.kit.container.modules.user_role_permission.role

import com.fasterxml.jackson.annotation.JsonIgnore
import com.life.kit.container.modules.BaseEntity
import com.life.kit.container.modules.user_role_permission.permission.PermissionEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.EAGER
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "core_role")
class RoleEntity : BaseEntity() {

    @Column(name = "name", nullable = false, unique = true)
    var name: String? = null

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "core_permission_to_role",
               joinColumns = [JoinColumn(name = "role_id")],
               inverseJoinColumns = [JoinColumn(name = "permission_id")])
    @JsonIgnore
    var permissions: Set<PermissionEntity>? = null
}
