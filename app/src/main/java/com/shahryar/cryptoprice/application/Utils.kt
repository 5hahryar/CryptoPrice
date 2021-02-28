package com.shahryar.cryptoprice.application

import android.content.Context

class Utils {

    /**
     * Write data to shared preferences
     */
    fun writePreference(context: Context, key: String, value: String) {
        val prefs = context.getSharedPreferences(SHARED_PREFRENCES, Context.MODE_PRIVATE)
        prefs.edit().putString(key, value).apply()
    }

    /**
     * Get string preference
     */
    fun readStringPreference(context: Context, key: String): String? {
        return context.getSharedPreferences(SHARED_PREFRENCES, Context.MODE_PRIVATE)
            .getString(key, null)
    }
}