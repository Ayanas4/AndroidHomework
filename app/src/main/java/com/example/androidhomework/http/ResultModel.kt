package com.example.androidhomework.http

/**
 * Created by Alex on 6/20/21.
 */

data class ResultModel<T>(val status: String, val message: String, val data: T? = null) {

    companion object {
        fun newErrorResultsModel(msg: String) : ResultModel<Any> {
            return ResultModel("fail", msg)
        }
    }

    fun isSuccess(): Boolean {
        return status == "success"
    }
}


