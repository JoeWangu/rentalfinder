package com.saddict.rentalfinder.rentals.model.remote

data class Rental(
    val name: String,
    val image: Int,
    val price: String,
//    val description: String,
    val type: String,
    val location: String,
//    val available: Boolean,
//    val rating: Int,
//    val total_units: Int,
//    val date_posted: String,
//    val date_modified: String
)
