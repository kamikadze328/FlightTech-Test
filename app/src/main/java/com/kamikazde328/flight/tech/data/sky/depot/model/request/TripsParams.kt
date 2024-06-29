package com.kamikazde328.flight.tech.data.sky.depot.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TripsParams(
    @SerialName("id")
    val id: String,
    @SerialName("company")
    val company: String,
) {
    companion object {
        val EMPTY = TripsParams(
            id = "10",
            company = DefaultRequestParams.EMPTY.company
        )
    }
}

