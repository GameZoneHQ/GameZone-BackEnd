package com.mobilebreakero.exceptions.response


object ApiResponseMessages {
    val USERNAME_REQUIRED = "Username is required"
    val PASSWORD_REQUIRED = "Password is required"
    val USER_TYPE_REQUIRED = "UserType is required"
    val INVALID_PASSWORD_FORMAT =
        "Invalid password format (min 6 characters, at least 1 uppercase, 1 lowercase, 1 number)"
    val EMAIL_REQUIRED = "Email is required"
    val INVALID_EMAIL_FORMAT = "Invalid email format"
    val USERNAME_ALREADY_EXISTS = "Username already exists"
    val EMAIL_ALREADY_REGISTERED = "This email already registered"
    val INTERNAL_SERVER_ERROR = "Internal Server Error"
    val EMAIL_NOT_FOUND = "Email not found"
    val INVALID_PASSWORD = "Invalid password"
    val INCORRECT_EMAIL_OR_PASSWORD = "Incorrect email or password"
}
