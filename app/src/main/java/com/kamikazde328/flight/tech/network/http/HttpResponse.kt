package com.kamikazde328.flight.tech.network.http

data class HttpResponse(
    val body: String,
    val headers: Map<String, List<String>>,
    val statusCode: Int,
)