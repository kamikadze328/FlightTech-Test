package com.kamikazde328.flight.tech.network.http

import java.io.IOException

fun interface HttpRequestInterceptor {
    @Throws(IOException::class)
    fun intercept(request: HttpRequest): HttpRequest

    companion object {
        inline operator fun invoke(crossinline block: (request: HttpRequest) -> HttpRequest): HttpRequestInterceptor =
            HttpRequestInterceptor { block(it) }
    }
}