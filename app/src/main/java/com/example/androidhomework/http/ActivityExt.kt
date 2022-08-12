package com.example.androidhomework.http
import org.chromium.base.ThreadUtils
fun runOnUI(callback: () -> Unit) {
    Thread {
        ThreadUtils.runOnUiThread {
            callback.invoke()
        }
    }.start()
}
