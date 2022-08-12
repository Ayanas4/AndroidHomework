package com.example.androidhomework.http

/**
 * Created by Alex on 6/21/21.
 */
interface ICallbackEvent<T> {
    fun onResponse(result: T)
}
