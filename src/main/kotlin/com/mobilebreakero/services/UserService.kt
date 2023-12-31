package com.mobilebreakero.services

import com.mobilebreakero.models.DeveloperModel
import com.mobilebreakero.models.PlayerModel
import com.mobilebreakero.models.UserType
import com.mobilebreakero.models.UsersModel
import com.mobilebreakero.repositories.DeveloperRepository
import com.mobilebreakero.repositories.PlayerRepository
import com.mobilebreakero.repositories.UserRepository
import com.mobilebreakero.utils.errorResponse
import com.mobilebreakero.utils.successResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository,
    private val playerRepository: PlayerRepository,
    private val developerRepository: DeveloperRepository
) {

    @Transactional
    fun register(userType: UserType, username: String, email: String, password: String): Any {
        try {
            when (userType) {

                UserType.DEVELOPER -> {
                    val developerModel = DeveloperModel(
                        username = username,
                        email = email,
                        password = password
                    )
                    developerRepository.save(developerModel)
                }

                UserType.PLAYER -> {
                    val playerModel = PlayerModel(
                        Username = username,
                        email = email,
                        Password = password
                    )
                    playerRepository.save(playerModel)
                }
            }
            return successResponse("Registration successful")
        } catch (e: Exception) {
            return errorResponse<Any>("Registration failed: ${e.message}", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    fun login(email: String): UsersModel? {
        return this.userRepository.findByEmail(email)
    }

}
