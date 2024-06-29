package com.kamikazde328.flight.tech.network.http

data class HttpRequest(
    val body: String,
    val methodType: String,
    val headers: Map<String, String>,
    val url: String,
)