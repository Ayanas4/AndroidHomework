package com.example.androidhomework.utils

class Constants {
    companion object {
        //api code
        const val API_STATUS_200 = 200
        const val API_STATUS_500 = 500
        const val HTTP_TIMEOUT = 60000L
        const val API_STATUS_EXPIRE = -5
        const val API_STATUS_SUCCESS = 0

        //api
        const val LOGIN_API = "https://inner.ixensor.com/InterviewAPI/android/interview.php?api=login"
        const val REGISTER_API = "https://inner.ixensor.com/InterviewAPI/android/interview.php?api=register"

        //api param
        const val NAME = "name"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val MAIL = "mail"
        const val PASSWORD = "passwd"

        const val PREF_TOKEN = "pref_token"
    }
}