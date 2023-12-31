package com.mobilebreakero.services.player

import com.mobilebreakero.models.PlayerModel
import com.mobilebreakero.repositories.player.auth.PlayerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class PlayerAuthService @Autowired constructor(
    private val playerRepository: PlayerRepository
) {
    fun login(username: String): PlayerModel? {
        return playerRepository.findByEmail(username)
    }
}