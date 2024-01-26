package com.saddict.rentalfinder.rentals.data.manager

interface PreferenceDataStore {
    fun getToken(): String
    suspend fun setToken(token: String?)
}