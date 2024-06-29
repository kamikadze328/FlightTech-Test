package com.kamikazde328.flight.tech.data.sky.depot.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaneRequestParams(
    @SerialName("trip_id")
    val tripId: String,
    @SerialName("company")
    val company: String,
    @SerialName("pos_id")
    val posId: String,
)