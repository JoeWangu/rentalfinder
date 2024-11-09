package com.saddict.rentalfinder.utils

object Constants {
    //    USER CONSTANTS
//    const val USERS_URL = "users/"
    const val USER_PROFILE_URL = "user-profile/"
    const val CREATE_USER_URL = "create-user-api/"
    const val LOGIN_URL = "login-user-api/"

    //    RENTAL CONSTANTS
    const val RENTAL_URL = "rentals/api/rentals/"
    const val RENTAL_MANAGE_URL = "rentals/api/manage_rentals/"
    const val SINGLE_RENTAL_URL = "rentals/api/manage_rentals/{id}/"
    const val RENTAL_IMAGE_URL = "rentals/api/images/"
    const val RENTAL_COUNTRY_URL = "rentals/api/countries/"
    const val RENTAL_CITY_URL = "rentals/api/cities/"
    const val RENTAL_STATE_URL = "rentals/api/states/"
    const val RENTAL_NEIGHBORHOOD_URL = "rentals/api/neighborhoods/"

//    const val RENTAL_EXTRA_IMAGE_URL = "rentals/api/extra_images/"
//    const val RENTAL_RATINGS = "rentals/api/ratings/"
//    const val RENTAL_FAVORITES = "rentals/api/favorites/"

    //    const val LOGOUT_URL = "logout-api/"
    const val TOKEN = "user_token"
    const val TIMEOUT_MILLIS = 5_000L
}