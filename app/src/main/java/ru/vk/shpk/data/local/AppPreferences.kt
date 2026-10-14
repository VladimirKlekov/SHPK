package ru.vk.shpk.data.local

import android.content.Context
import android.content.SharedPreferences
import ru.vk.shpk.dto.User

class AppPreferences private constructor(){
    private lateinit var preferences: SharedPreferences

    companion object{
        private val PREFERENCE_FILE_NAME = "APP_PREFERENCES"
        fun create(context: Context): AppPreferences{
            val appPreferences = AppPreferences()
            appPreferences.preferences = context.getSharedPreferences(PREFERENCE_FILE_NAME,0)
            return appPreferences
        }
    }

    val accessToken: String?
    get() = preferences.getString("ACCESS_TOKEN", null)
    fun storeAccessToken(accessToken:String){
        preferences.edit().putString("ACCESS_TOKEN", accessToken).apply()
    }

//    val userDetails: User
//        get() : User{
//            return User(
//                preferences.getLong("ID",0),
//                preferences.getString("USERNAME", null),
//                preferences.getString("STATUS", null)
//            )
//        }

//    fun storeUserDetails(user:User){
//        val editor: SharedPreferences.Editor = preferences.edit()
//        editor.putLong("ID", user.id).apply()
//        editor.putString("USERNAME", user.userName).apply()
//        editor.putString("STATUS", user.status).apply()
//    }

    fun clear(){
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}