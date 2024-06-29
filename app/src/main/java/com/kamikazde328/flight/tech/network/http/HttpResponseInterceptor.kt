package com.kamikazde328.flight.tech.network.http

import java.io.IOException

fun interface HttpResponseInterceptor {
    @Throws(IOException::class)
    fun intercept(request: HttpResponse): HttpResponse

    companion object {
        inline operator fun invoke(crossinline block: (response: HttpResponse) -> HttpResponse): HttpResponseInterceptor =
            HttpResponseInterceptor { block(it) }
    }
}