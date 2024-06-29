package com.kamikazde328.flight.tech.network

import com.kamikazde328.flight.tech.network.http.HttpClient
import com.kamikazde328.flight.tech.network.http.HttpRequest
import com.kamikazde328.flight.tech.network.http.HttpResponse
import com.kamikazde328.flight.tech.network.json.rpc.JsonRpcRequestBody
import com.kamikazde328.flight.tech.network.json.rpc.JsonRpcResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object JsonRpcApiClient {
    const val DEFAULT_HTTP_METHOD = "POST"
    val Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    inline fun <reified T> JsonRpcRequestBody<T>.toHttpRequest(url: String): HttpRequest {
        return HttpRequest(
            body = Json.encodeToString(this),
            methodType = DEFAULT_HTTP_METHOD,
            headers = emptyMap(),
            url = url,
        )
    }

    inline fun <reified Response> HttpResponse.toJsonRpcResponse(): JsonRpcResponse<Response> {
        return Json.decodeFromString(body)
    }

    suspend inline fun <reified Request, reified Response> HttpClient.fetchData(
        method: String, url: String, params: Request
    ): Result<Response> {
        val httpRequest = JsonRpcRequestBody.makeDefault(method, params).toHttpRequest(url)
        return sendRequest(httpRequest).toJsonRpcResponse<Response>().toResult()
    }

    fun <Response> JsonRpcResponse<Response>.toResult(): Result<Response> {
        return if (error != null) {
            Result.failure(Throwable(error.message))
        } else {
            result?.let { Result.success(it) } ?: Result.failure(Throwable("Empty response"))
        }
    }
}