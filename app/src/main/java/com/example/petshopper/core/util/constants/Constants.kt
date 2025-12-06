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

    fun getSearchPlaceholder(categoryType: String): String {
        return when (categoryType) {
            "Dogs" -> "Search for your favorite dogs.."
            "Cats" -> "Search for your favorite cats.."
            "Birds" -> "Search for your favorite birds.."
            "Reptiles" -> "Search for your favorite reptiles.."
            "Turtles" -> "Search for your favorite turtles.."
            else -> "Search for ${categoryType.lowercase()}.."
        }
    }

    object EmptyInventoryMessages {
        val title = "Uh oh! No pets found."
        val subtitle = "Inventory is empty for this category.\nPlease check back again later."
    }

    object ProfileOptionTitles {
        const val accountInfo = "Account Information"
        const val saved = "Saved"
        const val notifications = "Notifications"
        const val aboutApp = "About"
        const val logout = "Logout"
    }
}