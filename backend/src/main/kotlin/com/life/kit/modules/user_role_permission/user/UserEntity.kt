package com.life.kit.modules.user_role_permission.user

import com.life.kit.modules.BaseEntity
import com.life.kit.modules.user_role_permission.role.RoleEntity
import com.life.kit.modules.profile.ProfileEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "core_users")
class UserEntity : BaseEntity() {

    @Column(name = "username", nullable = false, unique = true)
    var username: String? = null

    @Column(name = "password_hash", nullable = false)
    var passwordHash: String? = null

    @Column(name = "email", nullable = false, unique = true)
    var email: String? = null

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    var profile: ProfileEntity? = null

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    var role: RoleEntity? = null
}
