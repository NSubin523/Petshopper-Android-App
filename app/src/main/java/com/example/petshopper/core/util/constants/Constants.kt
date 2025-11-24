package com.example.petshopper.core.util.constants

object Constants {
    const val BASE_URL = "http://10.0.2.2:8081/api/"
    const val DEFAULT_PAGE_START = 0
    const val DEFAULT_PAGE_SIZE = 10

    object HttpStatus {
        const val OK = 200
        const val CREATED = 201
        const val NO_CONTENT = 204
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
        const val INTERNAL_SERVER_ERROR = 500
    }

    enum class ScreenLabels {
        Home,
        Cart,
        Profile
    }

    enum class RegistrationStep {
        NAME,
        CREDENTIALS,
        PHONE,
        CONFIRM
    }
}