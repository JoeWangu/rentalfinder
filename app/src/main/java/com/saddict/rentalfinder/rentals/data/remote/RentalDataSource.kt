package com.saddict.rentalfinder.rentals.data.remote

//import com.saddict.rentalfinder.R
//import com.saddict.rentalfinder.rentals.model.remote.images.RentalImageResults
//import com.saddict.rentalfinder.rentals.model.remote.rentals.RentalResults
//
//object RentalDataSource {
//    val rentals = listOf(
//        RentalResults(1, "Genmack Hostels", R.drawable.proxy_image_1, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//        RentalResults(2, "Landmark Hostels", R.drawable.proxy_image_2, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//        RentalResults(3, "Patreon Hostels", R.drawable.proxy_image_3, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//        RentalResults(4, "Salama Hostels", R.drawable.proxy_image_4, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//        RentalResults(5, "Boulder Hostels", R.drawable.proxy_image_5, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//        RentalResults(6, "Torpedo Hostels", R.drawable.proxy_image_6, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//        RentalResults(7, "Hotspot Hostels", R.drawable.proxy_image_7, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//        RentalResults(8, "SmartGuess Hostels", R.drawable.proxy_image_8, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//        RentalResults(9, "Tabletop Hostels", R.drawable.proxy_image_9, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//        RentalResults(10, "Minified Hostels", R.drawable.proxy_image_10, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//        RentalResults(11, "Monalisa Hostels", R.drawable.proxy_image_12, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//        RentalResults(12, "Pour Homme Hostels", R.drawable.proxy_image_13, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//        RentalResults(13, "Bournevita Hostels", R.drawable.proxy_image_14, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//        RentalResults(14, "Waterworks Hostels", R.drawable.proxy_image_15, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//        RentalResults(15, "Ramtons Hostels", R.drawable.proxy_image_16, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//        RentalResults(16, "Royal Hostels", R.drawable.proxy_image_17, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//        RentalResults(17, "Levins Hostels", R.drawable.proxy_image_18, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//        RentalResults(18, "Executive Hostels", R.drawable.proxy_image_19, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//        RentalResults(19, "Lala Salama Hostels", R.drawable.proxy_image_20, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//        RentalResults(20, "Lala Salama Hostels", R.drawable.proxy_image_20, "5000", "BedSitter", "bedsitter", "Ngomongo", true, 3, 15, "15-03-2024", "16-04-2024", RentalImageResults(1, "", "imageName")),
//    )
//}