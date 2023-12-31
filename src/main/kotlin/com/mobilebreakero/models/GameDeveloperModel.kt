package com.mobilebreakero.models

import com.mobilebreakero.utils.PasswordEncoder
import jakarta.persistence.*


@Entity
@Table(name = "Developer")
data class GameDeveloperModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "Dev_name")
    var username: String = "",

    @Column(updatable = true, name = "Password")
    var password: String = "",

    @Column(unique = true, updatable = true, name = "Email")
    var email: String = "",

    @Column(updatable = true, name = "Country")
    var country: String = "",
) {
    fun matchPassword(password: String): Boolean {
        return PasswordEncoder.passwordEncoder.matches(password, this.password)
    }
}