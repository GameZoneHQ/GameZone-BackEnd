package com.mobilebreakero.repositories

import com.mobilebreakero.models.DeveloperModel
import com.mobilebreakero.models.PlayerModel
import com.mobilebreakero.models.UsersModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UsersModel, Long> {
    fun findByEmail(email: String): UsersModel?
}

interface PlayerRepository : JpaRepository<PlayerModel, Long> {
    fun findByEmail(email: String): PlayerModel?
}

interface DeveloperRepository : JpaRepository<DeveloperModel, Long> {
    fun findByEmail(email: String): DeveloperModel?
}