package com.mobilebreakero.kotlinspringbootauthentication

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class TestController {

    @PostMapping("/test")
    fun test(@RequestBody body: Map<String, String>): ResponseEntity<Map<String, String>> {
        val username = body["username"]
        val password = body["password"]

        if (username.isNullOrBlank() || password.isNullOrBlank()) {
            return ResponseEntity.badRequest().body(mapOf("error" to "Username and password are required"))
        }

        val response = mapOf("message" to "Received username: $username, password: $password")
        return ResponseEntity.ok(response)
    }
}
