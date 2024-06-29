package com.kamikazde328.flight.tech.network.json.rpc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonRpcRequestBody<T>(
    @SerialName("jsonrpc")
    val jsonRpcVersion: String,
    @SerialName("method")
    val method: String,
    @SerialName("params")
    val params: T,
    @SerialName("id")
    val id: Int,
) {
    companion object {
        const val DEFAULT_JSON_RPC_VERSION = "2.0"
        const val DEFAULT_ID = 1
        inline fun <reified T> makeDefault(method: String, params: T): JsonRpcRequestBody<T> =
            JsonRpcRequestBody(
                jsonRpcVersion = DEFAULT_JSON_RPC_VERSION,
                method = method,
                params = params,
                id = DEFAULT_ID,
            )
    }
}