package com.mobilebreakero.models

import jakarta.persistence.*


@Entity
@Table(name = "Developer")
data class DeveloperModel(
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
)
