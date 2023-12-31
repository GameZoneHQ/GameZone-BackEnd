package com.mobilebreakero.controllers

import com.mobilebreakero.dtos.*
import com.mobilebreakero.exceptions.EmailAlreadyExistsException
import com.mobilebreakero.exceptions.EmailAlreadyExistsExceptionDataIntegrity
import com.mobilebreakero.exceptions.GlobalException
import com.mobilebreakero.exceptions.UsernameAlreadyExistsException
import com.mobilebreakero.exceptions.response.ApiResponseMessages
import com.mobilebreakero.services.UserService
import com.mobilebreakero.services.apiDeveloper.ApiDeveloperAuthService
import com.mobilebreakero.services.gameDeveloper.GameDeveloperAuthService
import com.mobilebreakero.services.player.PlayerAuthService
import com.mobilebreakero.utils.*
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api")
class AuthController @Autowired constructor(
    private val userService: UserService,
    private val apiDeveloperAuthService: ApiDeveloperAuthService,
    private val gameDeveloperAuthService: GameDeveloperAuthService,
    private val playerAuthService: PlayerAuthService,
) {

    @PostMapping("/register")
    fun register(@RequestBody body: RegisterDTO): Any {

        if (body.username.isNullOrBlank()) {
            return errorResponse<Any>(ApiResponseMessages.USERNAME_REQUIRED, HttpStatus.BAD_REQUEST)
        }

        if (body.password.isNullOrBlank()) {
            return errorResponse<Any>(ApiResponseMessages.PASSWORD_REQUIRED, HttpStatus.BAD_REQUEST)
        } else if (!isValidPassword(body.password)) {
            return errorResponse<Any>(ApiResponseMessages.INVALID_PASSWORD_FORMAT, HttpStatus.BAD_REQUEST)
        }
        if (body.userType == null) {
            return errorResponse<Any>(ApiResponseMessages.USER_TYPE_REQUIRED, HttpStatus.BAD_REQUEST)
        }
        if (body.email.isNullOrBlank()) {
            return errorResponse<Any>(ApiResponseMessages.EMAIL_REQUIRED, HttpStatus.BAD_REQUEST)
        } else if (!isValidEmail(body.email)) {
            return errorResponse<Any>(ApiResponseMessages.INVALID_EMAIL_FORMAT, HttpStatus.BAD_REQUEST)
        }
        val password = BCryptPasswordEncoder().encode(body.password)

        try {
            userService.register(
                userType = body.userType,
                username = body.username,
                email = body.email,
                password = password
            )
            return successResponse(body.email)
        } catch (e: UsernameAlreadyExistsException) {
            return errorResponse<Any>(ApiResponseMessages.USERNAME_ALREADY_EXISTS, HttpStatus.CONFLICT)
        } catch (e: EmailAlreadyExistsException) {
            return errorResponse<Any>(ApiResponseMessages.EMAIL_ALREADY_REGISTERED, HttpStatus.CONFLICT)
        } catch (e: EmailAlreadyExistsExceptionDataIntegrity) {
            return if (e.message?.contains("unique index 'UK_mmvsaa658iag0w1uqb112du6k'") == true) {
                errorResponse<Any>(ApiResponseMessages.EMAIL_ALREADY_REGISTERED, HttpStatus.CONFLICT)
            } else {
                errorResponse(
                    "${ApiResponseMessages.INTERNAL_SERVER_ERROR}: ${e.message}",
                    HttpStatus.INTERNAL_SERVER_ERROR
                )
            }
        } catch (e: GlobalException) {
            errorResponse<Any>(
                "${ApiResponseMessages.INTERNAL_SERVER_ERROR}: ${e.message}",
                HttpStatus.INTERNAL_SERVER_ERROR
            )
        }
        return errorResponse<Any>(ApiResponseMessages.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @PostMapping("/game_developer/login")
    fun gameDeveloperLogin(@RequestBody body: GameDeveloperLoginDTO, response: HttpServletResponse): Any {

        if (body.username.isNullOrBlank() && body.password.isNullOrBlank()) {
            return errorResponse<Any>(ApiResponseMessages.INCORRECT_EMAIL_OR_PASSWORD, HttpStatus.BAD_REQUEST)
        }

        if (body.username.isNullOrBlank()) {
            return errorResponse<Any>(ApiResponseMessages.EMAIL_REQUIRED, HttpStatus.BAD_REQUEST)
        }

        val user = body.username.let { this.gameDeveloperAuthService.login(it) }
            ?: return errorResponse<Any>(ApiResponseMessages.EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND)

        if (body.password.isNullOrBlank()) {
            return errorResponse<Any>(ApiResponseMessages.PASSWORD_REQUIRED, HttpStatus.BAD_REQUEST)
        }

        if (!user.matchPassword(body.password)) {
            return errorResponse<Any>(ApiResponseMessages.INVALID_PASSWORD, HttpStatus.BAD_REQUEST)
        }

        val issuer = user.id.toString()

        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + jwtExpirationInMs))
            .signWith(SignatureAlgorithm.HS512, "secret").compact()

        val cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true

        response.addCookie(cookie)

        return successResponse(user.username)
    }

    @PostMapping("/api_developer/login")
    fun apiDeveloperLogin(@RequestBody body: ApiDeveloperLoginDTO, response: HttpServletResponse): Any {

        if (body.email.isNullOrBlank() && body.password.isNullOrBlank()) {
            return errorResponse<Any>(ApiResponseMessages.INCORRECT_EMAIL_OR_PASSWORD, HttpStatus.BAD_REQUEST)
        }

        if (body.email.isNullOrBlank()) {
            return errorResponse<Any>(ApiResponseMessages.EMAIL_REQUIRED, HttpStatus.BAD_REQUEST)
        }

        val user = body.email.let { this.apiDeveloperAuthService.login(it) }
            ?: return errorResponse<Any>(ApiResponseMessages.EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND)

        if (body.password.isNullOrBlank()) {
            return errorResponse<Any>(ApiResponseMessages.PASSWORD_REQUIRED, HttpStatus.BAD_REQUEST)
        }

        if (!user.matchPassword(body.password)) {
            return errorResponse<Any>(ApiResponseMessages.INVALID_PASSWORD, HttpStatus.BAD_REQUEST)
        }

        val issuer = user.id.toString()

        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + jwtExpirationInMs))
            .signWith(SignatureAlgorithm.HS512, "secret").compact()

        val cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true

        response.addCookie(cookie)

        return successResponse(user.email)
    }

    @PostMapping("/player/login")
    fun playerLogin(@RequestBody body: PlayerLoginDTO, response: HttpServletResponse): Any {

        if (body.username.isNullOrBlank() && body.password.isNullOrBlank()) {
            return errorResponse<Any>(ApiResponseMessages.INCORRECT_EMAIL_OR_PASSWORD, HttpStatus.BAD_REQUEST)
        }

        if (body.username.isNullOrBlank()) {
            return errorResponse<Any>(ApiResponseMessages.EMAIL_REQUIRED, HttpStatus.BAD_REQUEST)
        }

        val user = body.username.let { this.playerAuthService.login(it) }
            ?: return errorResponse<Any>(ApiResponseMessages.EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND)

        if (body.password.isNullOrBlank()) {
            return errorResponse<Any>(ApiResponseMessages.PASSWORD_REQUIRED, HttpStatus.BAD_REQUEST)
        }

        if (!user.matchPassword(body.password)) {
            return errorResponse<Any>(ApiResponseMessages.INVALID_PASSWORD, HttpStatus.BAD_REQUEST)
        }

        val issuer = user.id.toString()

        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + jwtExpirationInMs))
            .signWith(SignatureAlgorithm.HS512, "secret").compact()

        val cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true

        response.addCookie(cookie)

        return successResponse(user.Username)
    }

}