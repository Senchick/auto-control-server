package com.company.autocontrol.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class Role {
    USER, MODER, ADMIN;

    @JsonValue
    fun toValue(): Int = ordinal

    // security name with ROLE_
    val roleName: String
        get() = "ROLE_$name"
}
