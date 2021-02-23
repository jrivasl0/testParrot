package com.rivas.testparrot.utils

import android.content.Context

class Preferences {

    companion object {
        const val TOKEN = "TOKEN"
        const val REFRESH = "REFRESH"
        const val STORE_NAME = "NAME"
        const val STORE_ID = "ID"
        fun putData(key: String, data: String, context: Context) = context.getSharedPreferences("DATA",Context.MODE_PRIVATE).edit().putString(key, data).apply()
        fun getData(key: String, context: Context) = context.getSharedPreferences("DATA",Context.MODE_PRIVATE).getString(key,"")
    }
}