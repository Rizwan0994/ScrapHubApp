package com.example.scrapproject.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.scrapproject.utils.Constants.PREFS_TOKEN_FILE
import com.example.scrapproject.utils.Constants.USER_TOKEN

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun deleteToken( )
    {
       // prefs.edit().putString(USER_TOKEN, "").commit()

        val editor = prefs.edit()
        editor.putString(USER_TOKEN, "")
        editor.apply()
    }
    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
}