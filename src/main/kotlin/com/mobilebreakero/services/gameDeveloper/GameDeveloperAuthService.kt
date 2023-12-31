package com.mobilebreakero.services.gameDeveloper

import com.mobilebreakero.models.GameDeveloperModel
import com.mobilebreakero.repositories.gameDeveloper.auth.GameDeveloperRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GameDeveloperAuthService @Autowired constructor(
    private val gameDeveloper: GameDeveloperRepository
) {
    fun login(email: String): GameDeveloperModel? {
        return gameDeveloper.findByEmail(email)
    }
}