package com.kamikazde328.flight.tech.data.sky.depot.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TripsResultResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: Trips,
)