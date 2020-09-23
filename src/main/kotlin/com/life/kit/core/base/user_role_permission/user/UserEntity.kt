package com.life.kit.core.base.user_role_permission.user

import com.fasterxml.jackson.annotation.JsonIgnore
import com.life.kit.core.base.BaseEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "core_users")
class UserEntity : BaseEntity(), UserDetails {

    @JsonIgnore
    override fun getPassword(): String {
        return ""
    }

    @JsonIgnore
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isAccountNonLocked(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        TODO("Not yet implemented")
    }

    @JsonIgnore
    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return listOf(SimpleGrantedAuthority("ROLE_USER"))
    }
}