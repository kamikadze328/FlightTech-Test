package com.kamikazde328.flight.tech.network.json.rpc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonRpcError(
    @SerialName("message")
    val message: String,
)