package com.example.androidhomework.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidhomework.R
import com.example.androidhomework.http.*
import com.example.androidhomework.model.LoginModel
import com.example.androidhomework.model.UserinfoModel
import com.example.androidhomework.utils.Constants
import com.example.androidhomework.utils.Constants.Companion.LOGIN_API
import com.example.androidhomework.utils.Constants.Companion.MAIL
import com.example.androidhomework.utils.Constants.Companion.PASSWORD
import com.example.androidhomework.utils.UserInfoManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    fun init() {
        try {
            var token = UserInfoManager.getInstance().getToken(this)
            if (!token.equals("")) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.putExtra("token", token)
                this@LoginActivity.startActivity(intent)
            }

            btn_login.setOnClickListener {
                toLogin(et_email.text.toString(), et_password.text.toString())
            }

            tv_create.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
                this@LoginActivity.startActivity(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("error", e.toString())
        }
    }

    fun toLogin(email: String, password: String) {
        val params = HashMap<String, Any>()
        params[MAIL] = email
        params[PASSWORD] = password
        ApiPostFormData(this, LOGIN_API, params, object : HttpCallBack {
            @SuppressLint("NotifyDataSetChanged")
            override fun onSuccess(jsonStr: String?) {
                try {
                    val gson =
                        GsonBuilder().registerTypeAdapterFactory(MyTypeAdapterFactory<Any?>())
                            .create()
                    val model = gson.fromJson(jsonStr, LoginModel::class.java)
                    val status = model.status
                    val token = model.token
                    if (status == Constants.API_STATUS_SUCCESS) {

                        UserInfoManager.getInstance().setToken(this@LoginActivity, token)
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("token", token)
                        this@LoginActivity.startActivity(intent)

                    } else {
                        runOnUI {
                            Toast.makeText(this@LoginActivity, "incorrect account or password", Toast.LENGTH_LONG).show()
                        }

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("error", e.toString())
                }
            }

            override fun onFail(msg: String?) {
                Log.e("Failmsg", msg.toString())
            }
        })
    }
}