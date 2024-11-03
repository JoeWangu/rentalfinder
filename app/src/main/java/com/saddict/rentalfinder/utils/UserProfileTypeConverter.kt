package com.saddict.rentalfinder.utils

import androidx.room.TypeConverter
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.saddict.rentalfinder.rentals.model.remote.UserProfile
import com.saddict.rentalfinder.rentals.model.remote.UserProfileDetails

class UserProfileTypeConverter {
    private val objectMapper = jacksonObjectMapper()

    @TypeConverter
    fun fromUserProfile(userProfile: UserProfile?): String {
        return userProfile.let { objectMapper.writeValueAsString(it) }
    }

    @TypeConverter
    fun toUserProfile(userProfileString: String?): UserProfile? {
        return userProfileString?.let { objectMapper.readValue<UserProfile>(it) }
    }
}

class UserProfileDetailsConverter {
    private val objectMapper = jacksonObjectMapper()

    @TypeConverter
    fun toUserProfileDetails(userProfileDetailsString: String?): UserProfileDetails? {
        return userProfileDetailsString?.let { objectMapper.readValue<UserProfileDetails>(it) }
    }

    @TypeConverter
    fun fromUserProfileDetails(userProfileDetails: UserProfileDetails?): String {
        return userProfileDetails.let { objectMapper.writeValueAsString(it) }
    }
}