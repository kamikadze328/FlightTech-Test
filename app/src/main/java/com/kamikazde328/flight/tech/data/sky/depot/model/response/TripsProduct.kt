package com.kamikazde328.flight.tech.data.sky.depot.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TripsProduct(
    @SerialName("product_id")
    val id: String?,
    @SerialName("trip_id")
    val tripId: String?,
    @SerialName("quantity")
    val quantity: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("cost")
    val cost: String?,
    @SerialName("physical")
    val physical: String?,
)