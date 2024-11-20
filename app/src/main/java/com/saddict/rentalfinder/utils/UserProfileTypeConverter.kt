package com.saddict.rentalfinder.utils

import androidx.room.TypeConverter
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.saddict.rentalfinder.rentals.model.local.UserProfileEntity
import com.saddict.rentalfinder.rentals.model.remote.RentalUserProfileDetails

class RentalUserProfileTypeConverter {
    private val objectMapper = jacksonObjectMapper()

    @TypeConverter
    fun fromUserProfile(userProfileEntity: UserProfileEntity?): String {
        return userProfileEntity.let { objectMapper.writeValueAsString(it) }
    }

    @TypeConverter
    fun toRentalUserProfile(rentalUserProfileString: String?): UserProfileEntity? {
        return rentalUserProfileString?.let { objectMapper.readValue<UserProfileEntity>(it) }
    }
}

class RentalUserProfileDetailsConverter {
    private val objectMapper = jacksonObjectMapper()

    @TypeConverter
    fun toUserProfileDetails(rentalUserProfileString: String?): RentalUserProfileDetails? {
        return rentalUserProfileString?.let { objectMapper.readValue<RentalUserProfileDetails>(it) }
    }

    @TypeConverter
    fun fromUserProfileDetails(userProfileDetails: RentalUserProfileDetails?): String {
        return userProfileDetails.let { objectMapper.writeValueAsString(it) }
    }
}