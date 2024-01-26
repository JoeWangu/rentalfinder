package com.saddict.rentalfinder.utils

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
        imageDetail = RentalImage(image ?: 1, imageUrl)
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
        imageUrl = imageDetail.image
    )
}
