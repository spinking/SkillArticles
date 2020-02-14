package ru.skillbranch.skillarticles.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import ru.skillbranch.skillarticles.data.delegates.PrefDelegate

class PrefManager(context: Context) {
    internal val preferences: SharedPreferences by lazy { PreferenceManager(context).sharedPreferences }

    var storedBoolean by PrefDelegate(false)
    var storedString by PrefDelegate("")
    var storedFloat by PrefDelegate(0f)
    var storedInt by PrefDelegate(0)
    var storedLong by PrefDelegate(0)

    fun clearAll(){
        preferences.edit().clear().apply()
    }
}