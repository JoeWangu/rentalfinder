package com.saddict.rentalfinder.utils

import com.saddict.rentalfinder.rentals.model.local.images.ImageEntity
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalEntity
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalManageEntity
import com.saddict.rentalfinder.rentals.model.remote.User
import com.saddict.rentalfinder.rentals.model.remote.UserProfileDetails
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
        authorDetail = User(
            id = authorId,
            email = authorEmail,
            username = authorUsername,
            userProfile = authorProfileDetails,
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
        authorProfileDetails = authorDetail.userProfile ?: UserProfileDetails("Not Provided"),
//        authorFirstName = authorDetail.firstName,
//        authorLastName = authorDetail.lastName,
//        authorPhoneNumber = authorDetail.userProfile?.phoneNumber,
//        authorAddress = authorDetail.address,
//        authorDob = authorDetail.dob,
        imageUrl = imageDetail.imageUrl,
//        authorGender = authorDetail.gender,
        imageName = imageDetail.imageName,
        avgRating = avgRating,
        author = author,
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