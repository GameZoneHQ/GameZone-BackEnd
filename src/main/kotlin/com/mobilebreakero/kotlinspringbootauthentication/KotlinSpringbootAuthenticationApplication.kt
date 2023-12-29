package com.mobilebreakero.kotlinspringbootauthentication

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@AutoConfiguration
@EnableJpaRepositories(basePackages = ["com.mobilebreakero.repositories"])
@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@ComponentScan(basePackages = ["com.mobilebreakero"])
@EntityScan(basePackages = ["com.mobilebreakero.models"])
class KotlinSpringbootAuthenticationApplication

fun main(args: Array<String>) {
    runApplication<KotlinSpringbootAuthenticationApplication>(*args)
}
