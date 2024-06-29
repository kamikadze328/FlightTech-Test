package com.kamikazde328.flight.tech.data.sky.depot.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeatMapRequestParams(
    @SerialName("planes_ids")
    val planesIds: List<String>,
    @SerialName("company")
    val company: String,
)