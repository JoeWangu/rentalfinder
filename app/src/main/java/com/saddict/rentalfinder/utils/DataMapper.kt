package com.saddict.rentalfinder.utils

import com.saddict.rentalfinder.rentals.model.local.ImageEntity
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import com.saddict.rentalfinder.rentals.model.remote.RentalImage
import com.saddict.rentalfinder.rentals.model.remote.RentalResults

fun RentalEntity.mapToResults(): RentalResults{
    return RentalResults(
        id,
        name,
        image,
        price,
        description,
        type,
        location,
        available,
        rating,
        totalUnits,
        datePosted,
        dateModified,
        imageDetail = RentalImage(image ?: 1, imageUrl, imageName)
    )
}
fun RentalResults.mapToEntity(): RentalEntity{
    return RentalEntity(
        id,
        name,
        image,
        price,
        description,
        type,
        location,
        available,
        rating,
        totalUnits,
        datePosted,
        dateModified,
        imageUrl = imageDetail.imageUrl,
        imageName = imageDetail.imageName
    )
}

fun RentalImage.mapToImageEntity(): ImageEntity {
    return ImageEntity(
        id = id,
        imageUrl = imageUrl,
        imageName = imageName
    )
}

fun ImageEntity.mapToRentalImage(): RentalImage {
    return RentalImage(
        id = id,
        imageUrl = imageUrl,
        imageName = imageName
    )
}