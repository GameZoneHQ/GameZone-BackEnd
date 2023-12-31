package com.mobilebreakero.services.apiDeveloper

import com.mobilebreakero.models.ApiDeveloperModel
import com.mobilebreakero.models.GameDeveloperModel
import com.mobilebreakero.repositories.apiDeveloper.auth.ApiDeveloperRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ApiDeveloperAuthService @Autowired constructor(
    private val apiDeveloperRepository: ApiDeveloperRepository
) {
    fun login(username: String): ApiDeveloperModel? {
        return apiDeveloperRepository.findByEmail(username)
    }
}