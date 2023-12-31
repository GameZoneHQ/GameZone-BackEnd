package com.mobilebreakero.repositories.player.auth

import com.mobilebreakero.models.PlayerModel
import org.springframework.data.jpa.repository.JpaRepository


interface PlayerRepository : JpaRepository<PlayerModel, Long> {
    fun findByEmail(email: String): PlayerModel?
}