package com.example.androidhomework.http

interface HttpCallBack {
    fun onSuccess(jsonStr: String?)
    fun onFail(msg: String?)
}