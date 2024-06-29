package com.kamikazde328.flight.tech.network.json.rpc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonRpcResponse<T>(
    @SerialName("jsonrpc")
    val jsonRpcVersion: String,
    @SerialName("result")
    val result: T? = null,
    @SerialName("error")
    val error: JsonRpcError? = null,
    @SerialName("id")
    val id: Int,
    @SerialName("exec_time")
    val execTime: String,
)