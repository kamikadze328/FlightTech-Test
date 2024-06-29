package com.kamikazde328.flight.tech.network

import com.kamikazde328.flight.tech.data.security.HttpSecurityRepository
import com.kamikazde328.flight.tech.network.http.HttpClient

class HttpClientDefaultFactory(
    private val securityRepository: HttpSecurityRepository = HttpSecurityRepository.getInstance()
) {
    companion object {
        private const val AUTHORIZATION_HEADER = "authorization"
        private const val RTS_HEADER = "rts"
        private const val CONTENT_TYPE_HEADER = "content-type"
        private const val CONTENT_TYPE_HEADER_VALUE = "application/json"

        private var instance: HttpClientDefaultFactory? = null

        fun getInstance(): HttpClientDefaultFactory {
            return instance ?: HttpClientDefaultFactory().also {
                instance = it
            }
        }
    }

    fun makeHttpClient(): HttpClient {
        return HttpClient.Builder()
            .addAuthHeaders()
            .addJsonRpcHeaders()
            .build()
    }

    private fun HttpClient.Builder.addAuthHeaders(): HttpClient.Builder {
        val secrets = securityRepository.getNetworkSecrets()
        return this
            .header(AUTHORIZATION_HEADER, secrets.token)
            .header(RTS_HEADER, secrets.rts)
    }

    private fun HttpClient.Builder.addJsonRpcHeaders(): HttpClient.Builder {
        return this
            .header(CONTENT_TYPE_HEADER, CONTENT_TYPE_HEADER_VALUE)
    }
}