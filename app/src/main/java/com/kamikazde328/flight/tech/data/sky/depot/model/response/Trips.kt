package com.kamikazde328.flight.tech.data.sky.depot.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Trips(
    @SerialName("id")
    val id: String?,
    @SerialName("trips_products")
    val tripsProducts: List<TripsProduct>?,
)

