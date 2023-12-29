package com.mobilebreakero.services

import com.mobilebreakero.models.UsersModel
import com.mobilebreakero.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository,
) {

    fun register(usersModel: UsersModel): UsersModel {
        return this.userRepository.save(usersModel)
    }

    fun login(email: String): UsersModel? {
        return this.userRepository.findByEmail(email)
    }

}