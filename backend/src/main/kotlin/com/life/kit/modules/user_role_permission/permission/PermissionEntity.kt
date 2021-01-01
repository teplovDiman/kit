package com.life.kit.modules.user_role_permission.permission

import com.life.kit.modules.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "core_permission")
class PermissionEntity : BaseEntity() {

    @Column(name = "name", nullable = false, unique = true)
    var name: String? = null

    @Column(name = "description", nullable = false)
    var description: String? = null
}
