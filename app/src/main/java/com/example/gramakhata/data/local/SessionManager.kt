package com.example.gramakhata.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(@ApplicationContext context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("gramakhata_prefs", Context.MODE_PRIVATE)

    fun setLoggedIn(isLoggedIn: Boolean, phone: String? = null) {
        val editor = prefs.edit()
        editor.putBoolean("is_logged_in", isLoggedIn)
        if (phone != null) {
            editor.putString("logged_in_phone", phone)
        } else if (!isLoggedIn) {
            editor.remove("logged_in_phone")
        }
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }

    fun getLoggedInPhone(): String? {
        return prefs.getString("logged_in_phone", null)
    }

    fun setLanguage(language: String) {
        prefs.edit().putString("app_language", language).apply()
    }

    fun getLanguage(): String {
        return prefs.getString("app_language", "English") ?: "English"
    }
}
