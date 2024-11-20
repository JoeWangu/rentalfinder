package com.saddict.rentalfinder.utils

import com.saddict.rentalfinder.rentals.model.local.UserProfileEntity
import com.saddict.rentalfinder.rentals.model.local.images.ImageEntity
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalEntity
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalManageEntity
import com.saddict.rentalfinder.rentals.model.remote.CityResults
import com.saddict.rentalfinder.rentals.model.remote.CountryResults
import com.saddict.rentalfinder.rentals.model.remote.NeighborhoodResults
import com.saddict.rentalfinder.rentals.model.remote.RentalUser
import com.saddict.rentalfinder.rentals.model.remote.RentalUserProfileDetails
import com.saddict.rentalfinder.rentals.model.remote.StateResults
import com.saddict.rentalfinder.rentals.model.remote.UserProfile
import com.saddict.rentalfinder.rentals.model.remote.images.RentalImageResults
import com.saddict.rentalfinder.rentals.model.remote.rentals.RentalResults

fun RentalEntity.mapToResults(): RentalResults {
    return RentalResults(
        id = id,
        image = image,
        price = price,
        totalUnits = totalUnits,
        title = title,
        description = description,
        category = category,
//        location = location,
        datePosted = datePosted,
        dateModified = dateModified,
        timePosted = timePosted,
        timeModified = timeModified,
        available = available,
        isActive = isActive,
        authorDetail = RentalUser(
            id = authorId,
            email = authorEmail,
            username = authorUsername,
            userProfile = RentalUserProfileDetails(null),
//            firstName = authorFirstName,
//            lastName = authorLastName,
//            phoneNumber = authorPhoneNumber,
//            address = authorAddress,
//            dob = authorDob,
//            gender = authorGender,
        ),
        imageDetail = RentalImageResults(
            image ?: 1,
            imageName = imageName,
            imageUrl = imageUrl,
            author = author
        ),
        avgRating = avgRating,
        author = author,
        country = CountryResults(countryId, countryName, countryCode),
        state = StateResults(stateId, stateName, countryId),
        city = CityResults(cityId, cityName, stateId, countryId),
        neighborhood = NeighborhoodResults(neighborhoodId, neighborhoodName, cityId)
    )
}

fun RentalResults.mapToEntity(): RentalEntity {
    return RentalEntity(
        id = id,
        image = image,
        price = price,
        totalUnits = totalUnits,
        title = title,
        description = description,
        category = category,
//        location = location,
        datePosted = datePosted,
        dateModified = dateModified,
        timePosted = timePosted,
        timeModified = timeModified,
        available = available,
        isActive = isActive,
        authorId = authorDetail.id,
        authorEmail = authorDetail.email,
        authorUsername = authorDetail.username ?: "",
//        authorFirstName = authorDetail.firstName,
//        authorLastName = authorDetail.lastName,
//        authorPhoneNumber = authorDetail.userProfileEntity?.phoneNumber,
//        authorAddress = authorDetail.address,
//        authorDob = authorDetail.dob,
        imageUrl = imageDetail.imageUrl,
//        authorGender = authorDetail.gender,
        imageName = imageDetail.imageName,
        avgRating = avgRating,
        author = author,
        countryName = country?.name,
        stateName = state?.name,
        cityName = city?.name,
        neighborhoodName = neighborhood?.name,
        countryId = country?.id,
        stateId = state?.id,
        cityId = city?.id,
        neighborhoodId = neighborhood?.id,
        countryCode = country?.code,
        authorPhoneNumber = authorDetail.userProfile?.phoneNumber,
    )
}

fun RentalResults.mapToManageEntity(): RentalManageEntity {
    return RentalManageEntity(
        id = id,
        image = image,
        price = price,
        totalUnits = totalUnits,
        title = title,
        description = description,
        category = category,
//        location = location,
        datePosted = datePosted,
        dateModified = dateModified,
        timePosted = timePosted,
        timeModified = timeModified,
        available = available,
        isActive = isActive,
        authorId = authorDetail.id,
//        authorFirstName = authorDetail.firstName,
//        authorPhoneNumber = authorDetail.phoneNumber,
        authorEmail = authorDetail.email,
        imageUrl = imageDetail.imageUrl,
        imageName = imageDetail.imageName,
        avgRating = avgRating,
        author = author,
        authorUsername = authorDetail.username ?: "",
        authorPhoneNumber = authorDetail.userProfile?.phoneNumber,
        countryName = country?.name,
        stateName = state?.name,
        cityName = city?.name,
        neighborhoodName = neighborhood?.name,
        countryId = country?.id,
        stateId = state?.id,
        cityId = city?.id,
        neighborhoodId = neighborhood?.id,
        countryCode = country?.code,
    )
}

fun RentalImageResults.mapToImageEntity(): ImageEntity {
    return ImageEntity(
        id = id,
        imageName = imageName,
        imageUrl = imageUrl,
        author = author
    )
}

fun ImageEntity.mapToRentalImage(): RentalImageResults {
    return RentalImageResults(
        id = id,
        imageName = imageName,
        imageUrl = imageUrl,
        author = author
    )
}

fun UserProfile.mapToUserProfileEntity(): UserProfileEntity {
    return UserProfileEntity(
        id = 1,
        firstName = firstName,
        lastName = lastName,
        phoneNumber = phoneNumber,
        dob = dob,
        gender = gender,
        address = address,
        profilePicture = profilePicture,
        userBio = bio,
        userCountry = countryDetails.name,
        userState = stateDetails?.name,
        userCity = cityDetails?.name,
        userNeighborhood = neighborhoodDetails?.name
    )
}

fun UserProfileEntity.mapToUserProfile(): UserProfile {
    return UserProfile(
        firstName = firstName,
        lastName = lastName,
        phoneNumber = phoneNumber,
        dob = dob,
        gender = gender,
        address = address,
        profilePicture = profilePicture,
        bio = userBio ?: "",
        country = 1,
        state = 1,
        city = 1,
        neighborhood = 1,
        countryDetails = CountryResults(1, userCountry, "KE"),
        stateDetails = StateResults(1, userState, 1),
        cityDetails = CityResults(1, userCity, 1, 1),
        neighborhoodDetails = NeighborhoodResults(1, userNeighborhood, 1)
    )
}