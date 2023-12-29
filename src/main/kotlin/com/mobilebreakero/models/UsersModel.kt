package com.mobilebreakero.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Entity
@Table(name = "users_model")
class UsersModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "Username")
    var Username: String = ""

    @Column(name = "Password")
    var Password: String = ""
        @JsonIgnore
        get
        set(value) {
            val passwordEncoder = BCryptPasswordEncoder()
            field = passwordEncoder.encode(value)
        }

    @Column(unique = true, name = "Email")
    var email: String = ""

    @Column(name = "api_Key")
    var api_Key: String = ""

    @Column(name = "api_generated")
    var api_generated: Boolean = false

    fun matchPassword(password: String): Boolean {
        return BCryptPasswordEncoder().matches(password, this.Password)
    }
}