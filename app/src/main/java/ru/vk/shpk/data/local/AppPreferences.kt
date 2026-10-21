package ru.vk.shpk.data.local

import android.content.Context
import android.content.SharedPreferences
import ru.vk.shpk.dto.UserDetails

class AppPreferences private constructor() {
    private lateinit var preferences: SharedPreferences

    companion object {
        private val PREFERENCE_FILE_NAME = "APP_PREFERENCES"
        fun create(context: Context): AppPreferences {
            val appPreferences = AppPreferences()
            appPreferences.preferences = context.getSharedPreferences(PREFERENCE_FILE_NAME, 0)
            return appPreferences
        }
    }

    val accessToken: String?
        get() = preferences.getString("ACCESS_TOKEN", null)

    fun storeAccessToken(accessToken: String) {
        preferences.edit().putString("ACCESS_TOKEN", accessToken).apply()
    }

    val userDetails: UserDetails
        get() : UserDetails {
            return UserDetails(
                preferences.getString("USERNAME", null),
                preferences.getString("STATUS", null),
                preferences.getString("TOKEN", null)
            )
        }

    fun storeUserDetails(user: UserDetails) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString("TOKEN", user.token).apply()
        editor.putString("USERNAME", user.username).apply()
        editor.putString("STATUS", user.status).apply()
    }

    fun clear() {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}