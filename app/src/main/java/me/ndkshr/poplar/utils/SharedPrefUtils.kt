package me.ndkshr.poplar.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPrefUtils {

    private const val PREF_NAME = "PoplarPreferences"
    private lateinit var sharedPreferences: SharedPreferences

    // Initialize the SharedPreferences object
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // Save a string value
    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    // Get a string value
    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    // Save a boolean value
    fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    // Get a boolean value
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    // Clear specific key
    fun clearKey(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    // Clear all preferences
    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }
}
