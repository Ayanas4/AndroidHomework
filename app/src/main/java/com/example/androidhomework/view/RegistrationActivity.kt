package com.example.androidhomework.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidhomework.R
import com.example.androidhomework.http.*
import com.example.androidhomework.model.LoginModel
import com.example.androidhomework.utils.Constants
import com.example.androidhomework.utils.Constants.Companion.REGISTER_API
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        init()
    }

    fun init() {
        try {
            iv_back.setOnClickListener {
                finish()
            }

            btn_register.setOnClickListener {
                val name = tv_name.text.toString()
                val email = et_email.text.toString()
                val password = et_password.text.toString()
                toRegister(name, email, password)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("error", e.toString())
        }
    }

    fun toRegister(name: String, email: String, password: String) {
        val params = HashMap<String, Any>()
        params[Constants.FIRST_NAME] = name
        params[Constants.LAST_NAME] = ""
        params[Constants.MAIL] = email
        params[Constants.PASSWORD] = password
        ApiPostFormData(this, REGISTER_API, params, object : HttpCallBack {
            override fun onSuccess(jsonStr: String?) {
                val gson =
                    GsonBuilder().registerTypeAdapterFactory(MyTypeAdapterFactory<Any?>())
                        .create()
                val model = gson.fromJson(jsonStr, LoginModel::class.java)
                val status = model.status
                if (status == Constants.API_STATUS_SUCCESS) {
                    try {
                        runOnUI {
                            Toast.makeText(this@RegistrationActivity, "resister success", Toast.LENGTH_LONG).show()
                            finish()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e("error", e.toString())
                    }
                }
            }

            override fun onFail(msg: String?) {
                Log.e("Failmsg", msg.toString())
            }
        })
    }
}