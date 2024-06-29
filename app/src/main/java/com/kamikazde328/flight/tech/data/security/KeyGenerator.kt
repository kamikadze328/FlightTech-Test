package com.kamikazde328.flight.tech.data.security

import android.util.Base64
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object KeyGenerator {
    fun genkey(rts: Long): String {
        val date = Date(rts)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val ctime = dateFormat.format(date)

        val cryptor = Cryptor()

        val sourceA = String.format(
            "%sTEST%sTASK%s",
            (ctime[13].code + 1) * (ctime[15].code + 1),
            ctime.substring(0, 17),
            ctime.substring(0, 17)
        )

        val sourceB = String.format(
            "DOIT%sPLS%s%s",
            ctime.substring(12, 17),
            ctime.substring(12, 17),
            (ctime[13].code + 1) * (ctime[15].code + 3)
        )

        val key = hash("SHA-512", sourceA).substring(0, 32).uppercase(Locale.US)
        val iv = hash("SHA-512", sourceB).substring(16, 32).uppercase(Locale.US)

        cryptor.updateKey(key)
        cryptor.updateIV(iv)

        val apikey = String.format(
            "%s%s%s",
            cryptor.encrypt(key).trim(),
            cryptor.encrypt("justfortesttask").trim(),
            cryptor.encrypt(iv).trim()
        )

        return apikey
    }

    private fun hash(algorithm: String, data: String): String {
        val bytes = MessageDigest.getInstance(algorithm).digest(data.toByteArray())
        val sb = StringBuilder()
        for (byte in bytes) {
            sb.append(String.format("%02x", byte))
        }
        return sb.toString()
    }

    private class Cryptor {
        companion object {
            const val METHOD = "AES/CBC/PKCS5Padding"
        }

        private var instanceKey: String = ""
        private var instanceIv: String = ""

        fun updateIV(newIv: String) {
            instanceIv = newIv
        }

        fun updateKey(newKey: String) {
            instanceKey = newKey
        }

        fun encrypt(string: String): String {
            val cipher = Cipher.getInstance(METHOD)
            val secretKey = SecretKeySpec(instanceKey.toByteArray(Charsets.UTF_8), "AES")
            val ivSpec = IvParameterSpec(instanceIv.toByteArray(Charsets.UTF_8))
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)
            val encrypted = cipher.doFinal(string.toByteArray(Charsets.UTF_8))
            return Base64.encodeToString(encrypted, Base64.DEFAULT)
        }

        fun decrypt(encrypted: String): String {
            val cipher = Cipher.getInstance(METHOD)
            val secretKey = SecretKeySpec(instanceKey.toByteArray(Charsets.UTF_8), "AES")
            val ivSpec = IvParameterSpec(instanceIv.toByteArray(Charsets.UTF_8))
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)
            val decodedBytes = Base64.decode(encrypted, Base64.DEFAULT)
            val decrypted = cipher.doFinal(decodedBytes)
            return String(decrypted, Charsets.UTF_8)
        }
    }
}