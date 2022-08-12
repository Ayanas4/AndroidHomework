package com.example.myapplication.http

import com.example.androidhomework.http.HttpUtils
import com.example.androidhomework.http.ICallbackEvent
import com.example.androidhomework.http.JsonUtility
import com.example.androidhomework.http.ResultModel
import com.google.gson.JsonSyntaxException
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException


/**
 * Created by Alex on 6/20/21.
 */
object RequestWrapper {

    private const val TAG = "RequestWrapper"
    private const val API_FAILED_MSG = "apiFailed"
    private val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()
    private val logging = HttpLoggingInterceptor().also {
        it.level = (HttpLoggingInterceptor.Level.BODY)
    }
    val client = OkHttpClient()
        .newBuilder()
        .addInterceptor(logging)
        .sslSocketFactory(HttpUtils.initSSLSocketFactory(), HttpUtils.initTrustManager())
        .build()


    fun get(url: String, callback: ICallbackEvent<String>) {

        val request: Request = Request.Builder()
            .url(url)
            .get()
//            .addHeader(MsgConstant.API_KEY_LABEL, MsgConstant.API_KEY_IM)
            .build()

        newCall(request, callback)
    }

    private fun newBody(params: Map<String, Any>): RequestBody {
        val requestBody: MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM)

        params.forEach { (key, value) -> requestBody.addFormDataPart(key, value.toString()) }

        return requestBody.build()
    }

    private fun newCall(request: Request, callback: ICallbackEvent<String>) {

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onResponse(
                    JsonUtility.toJson(
                        ResultModel.newErrorResultsModel(
                            API_FAILED_MSG
                        )
                    )
                )
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    response.body?.let {
                        try {
                            var jsonStr = it.string()
                            //Timber.e("newCall="+jsonStr)
                            //有時長這樣
                            // Undefined variable: this_order_shipping_fee{"status":"fail","message":"\u5546\u54c1\u898f\u683c\u932f\u8aa4,\u7121\u6cd5\u4e0b\u8a02\u55ae","data":[]}
                            jsonStr = jsonStr.substring(jsonStr.indexOf("{"))

                            val status = JsonUtility.getResponseStatus(jsonStr)
                            if(status== "200") {
                                callback.onResponse(jsonStr)
                            } else {
                                val msg = JsonUtility.getResponseMessage(jsonStr)
                                callback.onResponse(
                                    JsonUtility.toJson(
                                        ResultModel.newErrorResultsModel(
                                            msg
                                        )
                                    )
                                )
                            }

                        } catch (ex: JsonSyntaxException) {
                            callback.onResponse(
                                JsonUtility.toJson(
                                    ResultModel.newErrorResultsModel(
                                        "Response JSON format is not matched to the model"
                                    )
                                )
                            )
                        } catch (ex: Exception) {
                            callback.onResponse(
                                JsonUtility.toJson(
                                    ResultModel.newErrorResultsModel(
                                        API_FAILED_MSG
                                    )
                                )
                            )
                        }
                    } ?: callback.onResponse(JsonUtility.toJson(ResultModel.newErrorResultsModel("Response body is null.")))
                } else {
                    response.body?.let {
                        callback.onResponse(JsonUtility.toJson(ResultModel.newErrorResultsModel("error")))
                    }
                }

            }
        })

    }

}