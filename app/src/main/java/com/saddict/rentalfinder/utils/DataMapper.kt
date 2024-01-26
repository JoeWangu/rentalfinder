package com.saddict.rentalfinder.utils

import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import com.saddict.rentalfinder.rentals.model.remote.Rental
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
        dateModified
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
        dateModified
    )
}
