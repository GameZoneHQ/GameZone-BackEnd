package com.mobilebreakero.repositories.gameDeveloper.auth

import com.mobilebreakero.models.GameDeveloperModel
import com.mobilebreakero.models.PlayerModel
import org.springframework.data.jpa.repository.JpaRepository


interface GameDeveloperRepository : JpaRepository<GameDeveloperModel, Long> {
    fun findByEmail(email: String): GameDeveloperModel?
}