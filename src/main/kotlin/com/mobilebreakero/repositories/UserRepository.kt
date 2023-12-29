package com.mobilebreakero.repositories

import com.mobilebreakero.models.UsersModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UsersModel, Long> {
    fun findByEmail(email: String): UsersModel?
}