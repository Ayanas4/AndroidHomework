package com.example.androidhomework.http
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

open class HttpUtils {
    companion object {

        @JvmStatic
        fun initSSLSocketFactory(): SSLSocketFactory {
            var sslContext: SSLContext? = null
            try {
                sslContext = SSLContext.getInstance("SSL")
                val xTrustArray = arrayOf(initTrustManager())
                sslContext.init(
                    null,
                    xTrustArray, SecureRandom()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }


            return sslContext!!.socketFactory
        }

        @JvmStatic
        fun initTrustManager(): X509TrustManager {
            return object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }
            }
        }
    }
}