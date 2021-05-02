package com.shahryar.cryptoprice.application

import android.content.Context

class Utils {

    /**
     * Write data to shared preferences
     */
    fun writePreference(context: Context, key: String, value: Int): Boolean {
        val prefs = context.getSharedPreferences(SHARED_PREFRENCES, Context.MODE_PRIVATE)
       return prefs.edit().putInt(key, value).commit()
    }

    /**
     * Write data to shared preferences
     */
    fun removePreference(context: Context, key: String): Boolean {
        val prefs = context.getSharedPreferences(SHARED_PREFRENCES, Context.MODE_PRIVATE)
        return prefs.edit().remove(key).commit()
    }



    /**
     * Get string preference
     */
    fun readStringPreferenceInt(context: Context, key: String): Int {
        return context.getSharedPreferences(SHARED_PREFRENCES, Context.MODE_PRIVATE)
            .getInt(key, 0)
    }
}