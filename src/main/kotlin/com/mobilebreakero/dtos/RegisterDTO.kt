package com.mobilebreakero.dtos

import com.mobilebreakero.models.UserType

data class RegisterDTO(
    val username: String?,
    val password: String?,
    val email: String?,
    val userType: UserType?
)