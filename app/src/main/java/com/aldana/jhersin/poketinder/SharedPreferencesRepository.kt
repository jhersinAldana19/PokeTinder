package com.aldana.jhersin.poketinder

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesRepository {

    companion object {
        private lateinit var sharedPreferences: SharedPreferences
        private const val SHARED_PREFERENCES_KEY = "SHARED_PREFERENCES_KEY"
        private const val USER_EMAIL_KEY = "USER_EMAIL_KEY"
        private const val USER_PASSWORD_KEY = "USER_PASSWORD_KEY"
        private const val AUTH_KEY = "AUTH_KEY"
    }

    fun setSharedPreferences(context: Context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }

    fun getUserEmail(): String {
        return sharedPreferences.getString(USER_EMAIL_KEY, "") ?: ""
    }

    fun getUserPassword(): String {
        return sharedPreferences.getString(USER_PASSWORD_KEY, "") ?: ""
    }

    fun saveUserEmail(email: String) {
        sharedPreferences
            .edit()
            .putString(USER_EMAIL_KEY, email)
            .apply()
    }

    fun saveUserPassword(password: String) {
        sharedPreferences
            .edit()
            .putString(USER_PASSWORD_KEY, password)
            .apply()
    }

    fun getAuthKey(): String {
        return sharedPreferences.getString(AUTH_KEY, "") ?: ""
    }

    fun saveAuthKey(authKey: String) {
        sharedPreferences
            .edit()
            .putString(AUTH_KEY, authKey)
            .apply()
    }
}



