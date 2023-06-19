package com.company.autocontrol.enums

enum class Role {
    USER, MODER, ADMIN;

    // security name with ROLE_
    val roleName: String
        get() = "ROLE_$name"
}
