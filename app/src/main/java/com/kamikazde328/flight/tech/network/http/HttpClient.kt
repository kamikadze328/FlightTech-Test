package com.kamikazde328.flight.tech.network.http

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class HttpClient internal constructor(
    builder: Builder
) {
    companion object {
        private const val TAG = "HttpClient"
    }

    private val requestInterceptors: List<HttpRequestInterceptor> = builder.requestInterceptors
    private val responseInterceptors: List<HttpResponseInterceptor> = builder.responseInterceptors
    private val headers: Map<String, String> = builder.headers

    class Builder {
        internal val requestInterceptors: MutableList<HttpRequestInterceptor> = mutableListOf()
        internal val responseInterceptors: MutableList<HttpResponseInterceptor> = mutableListOf()
        internal val headers: MutableMap<String, String> = mutableMapOf()

        fun build(): HttpClient {
            return HttpClient(this)
        }

        fun addRequestInterceptor(interceptor: HttpRequestInterceptor): Builder = apply {
            requestInterceptors.add(interceptor)
        }

        fun addResponseInterceptor(interceptor: HttpResponseInterceptor): Builder = apply {
            responseInterceptors.add(interceptor)
        }

        fun header(name: String, value: String): Builder = apply {
            headers[name] = value
        }
    }


    suspend fun sendRequest(request: HttpRequest): HttpResponse {
        val updatedRequest = request.invokeRequestInterceptors()
        val result = request(updatedRequest)
        val updatedResult = result.invokeResponseInterceptors()

        return updatedResult
    }

    private suspend fun request(request: HttpRequest): HttpResponse = withContext(Dispatchers.IO) {
        val url = URL(request.url)

        with(url.openConnection() as HttpURLConnection) {
            requestMethod = request.methodType
            setHeader(headers)
            writeBody(request.body)
            val body = readBody()

            Log.d(TAG, "Request $requestMethod to $url")
            Log.d(TAG, "Request headers: $headers")
            Log.d(TAG, "Request body: ${request.body}")
            Log.d(TAG, "Response code: $responseCode")
            Log.d(TAG, "Response headers: $headerFields")
            Log.d(TAG, "Response body: $body")

            return@withContext HttpResponse(
                body = body,
                headers = headerFields,
                statusCode = responseCode,
            )
        }
    }

    private fun HttpURLConnection.writeBody(body: String?) {
        if (body.isNullOrEmpty()) return
        outputStream.bufferedWriter().use {
            it.write(body)
        }
    }

    private fun HttpURLConnection.setHeader(headers: Map<String, String>) {
        headers.forEach { (key, value) -> setRequestProperty(key, value) }
    }

    private fun HttpURLConnection.readBody(): String {
        return inputStream.bufferedReader().use {
            it.readText()
        }
    }

    private fun HttpRequest.invokeRequestInterceptors(): HttpRequest {
        var updatedRequest = this
        requestInterceptors.forEach { updatedRequest = it.intercept(updatedRequest) }
        return updatedRequest
    }

    private fun HttpResponse.invokeResponseInterceptors(): HttpResponse {
        var updatedResponse = this
        responseInterceptors.forEach { updatedResponse = it.intercept(updatedResponse) }
        return updatedResponse
    }
}