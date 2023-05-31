package com.company.autocontrol.security

import com.company.autocontrol.entity.UserEntity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(private val userEntity: UserEntity) : UserDetails {
    override fun getAuthorities() = listOf(SimpleGrantedAuthority(userEntity.role.name))
    override fun getPassword() = userEntity.password
    override fun getUsername() = userEntity.login
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}
