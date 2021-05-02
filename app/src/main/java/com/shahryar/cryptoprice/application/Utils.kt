package com.shahryar.cryptoprice.application

import android.content.Context

class Utils {

    /**
     * Write data to shared preferences
     */
    fun writePreference(context: Context, key: String, value: String): Boolean {
        val prefs = context.getSharedPreferences(SHARED_PREFRENCES, Context.MODE_PRIVATE)
        return prefs.edit().putString(key, value).commit()
    }

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
    fun readStringPreference(context: Context, key: String): String? {
        return context.getSharedPreferences(SHARED_PREFRENCES, Context.MODE_PRIVATE)
            .getString(key, null)
    }

    /**
     * Get string preference
     */
    fun readStringPreferenceInt(context: Context, key: String): Int {
        return context.getSharedPreferences(SHARED_PREFRENCES, Context.MODE_PRIVATE)
            .getInt(key, 0)
    }
}