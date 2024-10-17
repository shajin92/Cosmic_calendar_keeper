package com.sendil.cosmiccalenderkeeper.utils

import android.content.Context

class StorageUtils(private val mContext: Context) {

    fun getJsonFromAssets(fileName : String): String {
        val bufferReader = mContext.assets.open(fileName).bufferedReader()
        val data = bufferReader.use {
            it.readText()
        }

        return data
    }
}