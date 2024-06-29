package com.kamikazde328.flight.tech.data.sky.depot.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DefaultRequestParams(
    @SerialName("company")
    val company: String,
) {
    companion object {
        val EMPTY = DefaultRequestParams(
            company = "wa"
        )
    }
}