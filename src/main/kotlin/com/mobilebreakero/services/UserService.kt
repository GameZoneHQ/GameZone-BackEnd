package com.mobilebreakero.services

import com.mobilebreakero.models.ApiDeveloperModel
import com.mobilebreakero.models.GameDeveloperModel
import com.mobilebreakero.models.PlayerModel
import com.mobilebreakero.models.UserType
import com.mobilebreakero.repositories.apiDeveloper.auth.ApiDeveloperRepository
import com.mobilebreakero.repositories.gameDeveloper.auth.GameDeveloperRepository
import com.mobilebreakero.repositories.player.auth.PlayerRepository
import com.mobilebreakero.utils.errorResponse
import com.mobilebreakero.utils.successResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserService @Autowired constructor(
    private val playerRepository: PlayerRepository,
    private val apiDeveloperRepository: ApiDeveloperRepository,
    private val gameDeveloperRepository: GameDeveloperRepository
) {

    @Transactional
    fun register(userType: UserType, username: String, email: String, password: String): Any {
        try {
            when (userType) {

                UserType.API_DEVELOPER -> {
                    val apiDeveloperModel = ApiDeveloperModel(
                        username = username,
                        email = email,
                        password = password
                    )
                    apiDeveloperRepository.save(apiDeveloperModel)
                }

                UserType.PLAYER -> {
                    val playerModel = PlayerModel(
                        Username = username,
                        email = email,
                        Password = password
                    )
                    playerRepository.save(playerModel)
                }

                UserType.GAME_DEVELOPER -> {
                    val gameDeveloperModel = GameDeveloperModel(
                        username = username,
                        email = email,
                        password = password
                    )
                    gameDeveloperRepository.save(gameDeveloperModel)
                }
            }
            return successResponse("Registration successful")
        } catch (e: Exception) {
            return errorResponse<Any>("Registration failed: ${e.message}", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}
