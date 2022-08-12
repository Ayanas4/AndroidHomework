package com.example.androidhomework.http

import android.content.Context
import com.example.myapplication.http.RequestWrapper
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.io.IOException

class ApiGetQuery(val context: Context?, val url: String, val param: HashMap<String, Any>, val httpCallBack: HttpCallBack) {
    init {
        val header = HashMap<String, Any>().apply {
           // put(API_KEY_LABEL, API_KEY_IM)
        }
        HttpGet(context, url, param, header, httpCallBack)
    }
}

class HttpGet(
    val context: Context?,
    val url: String,
    val param: HashMap<String, Any> = HashMap(),
    val header: HashMap<String, Any> = HashMap(),
    val httpCallBack: HttpCallBack
) {
    var tag = "ImApiGet "
    private fun httpGet() {
        val client = RequestWrapper.client
        val request: Request = Request.Builder()
            .url(getQueryUrl())
            .apply {
                header.forEach { (key, value) ->
                    addHeader(key, value.toString())
                }
            }
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val body = response.body!!.string()

                when(response.code) {
                    500 -> {
                        httpCallBack.onFail("Sorry, Http 500 Error")
                    }
                    200 -> {
                        httpCallBack.onSuccess(body)
                    }
                    else -> {
                        var errorMsg = JsonUtility.getResponseError(body)
                        if (errorMsg.isEmpty()) {
                            errorMsg = JsonUtility.getResponseMessage(body)
                            if (errorMsg.isEmpty()) {
                                errorMsg = body
                            }
                        }
                        httpCallBack.onFail(errorMsg)
                    }
                }
                response.body!!.close()
            }
        })
    }

    private fun getQueryUrl(): String {
        val builder: HttpUrl.Builder = url.toHttpUrlOrNull()!!.newBuilder()
        param.forEach { (key, value) ->
            builder.addQueryParameter(key, value.toString())
        }
        return builder.build().toString()
    }

    init {
        httpGet()
    }
}