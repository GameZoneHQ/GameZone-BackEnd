package com.mobilebreakero.repositories.apiDeveloper.auth

import com.mobilebreakero.models.ApiDeveloperModel
import com.mobilebreakero.models.GameDeveloperModel
import org.springframework.data.jpa.repository.JpaRepository


interface ApiDeveloperRepository : JpaRepository<ApiDeveloperModel, Long> {
    fun findByEmail(email: String): ApiDeveloperModel?
}