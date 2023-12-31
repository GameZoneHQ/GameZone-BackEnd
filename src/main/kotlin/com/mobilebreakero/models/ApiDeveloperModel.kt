package com.mobilebreakero.models

import com.mobilebreakero.utils.PasswordEncoder
import jakarta.persistence.*
import java.time.LocalDateTime
import javax.management.relation.Role


@Entity
@Table(name = "Api_Developer")
data class ApiDeveloperModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "username")
    var username: String = "",

    @Column(updatable = true, name = "Password")
    var password: String = "",

    @Column(unique = true, updatable = true, name = "email")
    var email: String = "",

    @Column(updatable = true, name = "api_key")
    var apiKey: String = "",

    @Column(updatable = true, name = "hasApiKey")
    var hasApiKey: Boolean = false,

    @ElementCollection(targetClass = UserType::class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "developer_roles", joinColumns = [JoinColumn(name = "developer_id")])
    var roles: Set<UserType> = emptySet(),

    @Column(updatable = true, name = "lastAccess")
    var lastAccess: LocalDateTime? = null,

    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime? = null,

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null

) {
    fun matchPassword(password: String): Boolean {
        return PasswordEncoder.passwordEncoder.matches(password, this.password)
    }
}