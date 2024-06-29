package com.kamikazde328.flight.tech.data.security

class HttpSecurityRepository(
    private val keyGenerator: KeyGenerator = KeyGenerator
) {
    companion object {
        private var instance: HttpSecurityRepository? = null
        fun getInstance(): HttpSecurityRepository {
            return instance ?: HttpSecurityRepository().also { instance = it }
        }
    }

    fun getNetworkSecrets(): ApiSecrets {
        val rts = getRts()
        val token = keyGenerator.genkey(rts)

        return ApiSecrets(
            rts = rts.toServerTime(),
            token = token
        )
    }

    private fun Long.toServerTime(): String = (this / 1000).toString()

    private fun getRts(): Long {
        return System.currentTimeMillis()
    }
}

