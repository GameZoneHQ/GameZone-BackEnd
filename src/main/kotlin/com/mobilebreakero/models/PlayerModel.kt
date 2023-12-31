package com.mobilebreakero.models

import jakarta.persistence.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Entity
@Table(name = "Player")
data class PlayerModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(updatable = true, name = "user_name")
    var Username: String = "",

    @Column(updatable = true, name = "Password")
    var Password: String = "",

    @Column(unique = true, updatable = true, name = "Email")
    var email: String = "",

    @Column(updatable = true, name = "Gender")
    var gender: String = "",

    @Column(updatable = true, name = "Age")
    var age: Int = 0
) {
    fun matchPassword(password: String): Boolean {
        return BCryptPasswordEncoder().matches(password, this.Password)
    }
}