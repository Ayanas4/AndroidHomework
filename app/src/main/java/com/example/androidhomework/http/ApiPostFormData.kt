package com.example.androidhomework.http

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import com.example.androidhomework.R
import com.example.androidhomework.utils.Constants.Companion.API_STATUS_200
import com.example.androidhomework.utils.Constants.Companion.API_STATUS_500
import com.example.androidhomework.utils.Constants.Companion.API_STATUS_EXPIRE
import com.example.androidhomework.utils.Constants.Companion.HTTP_TIMEOUT
import com.example.myapplication.http.RequestWrapper
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiPostFormData(
    var context: Context,
    var url: String,
    var data: HashMap<String, Any>,
    var httpCallBack: HttpCallBack?
) {
    init {
        HttpPostFormData(context, url, data, httpCallBack)
    }
}

class HttpPostFormData(
    var context: Context,
    var url: String,
    var data: HashMap<String, Any> = HashMap(),
    var httpCallBack: HttpCallBack?
) {
    var tag = "ImApiPostForm "
    private fun httpPostFormData() {
        val client = RequestWrapper.client
        val request: Request = Request.Builder()
            .url(url)
            .post(getBody())
            .build()

        client.newBuilder().readTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS).build().newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body!!.string()

                    if (response.code == API_STATUS_200) {
                        if (body.isEmpty() && httpCallBack == null) {
                            return
                        }
                        val status = JsonUtility.getResponseStatus(body)
//                        if (status == API_STATUS_EXPIRE||body.contains("login faild")) { //有時status=success, EXPIRE藏在body
//
//
//                        } else
                        httpCallBack?.onSuccess(body)

                    } else if (response.code == API_STATUS_500) {
                        httpCallBack?.onFail("Sorry, Server 500 Error")
                    } else {
                        var errorMsg = JsonUtility.getResponseError(body)
                        if (errorMsg.isEmpty()) {
                            errorMsg = JsonUtility.getResponseMessage(body)
                            if (errorMsg.isEmpty()) {
                                errorMsg = body
                            }
                        }

                        httpCallBack?.onFail(errorMsg)

                    }
                    response.body!!.close()
                }
            })
    }

    private fun getBody(): RequestBody {
        return if (data.isEmpty()) {
            FormBody.Builder().build()
        } else {
            val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)

            data.forEach { (key, value) -> requestBody.addFormDataPart(key, value.toString()) }

            requestBody.build()
        }
    }

    init {
        httpPostFormData()
    }

}


