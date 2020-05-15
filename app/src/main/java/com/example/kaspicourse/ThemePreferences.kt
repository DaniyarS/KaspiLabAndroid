package com.example.kaspicourse

import android.content.Context

const val PREFERENCE = "myPreference"
const val THEME_STATE = "ThemeState"

class ThemePreferences (context: Context) {

    val preference = context.getSharedPreferences(PREFERENCE,Context.MODE_PRIVATE)

    fun getThemeState() : String? {
        return preference.getString(THEME_STATE, "White")
    }

    fun setThemeState(state: String) {
        val editor = preference.edit()
        editor.putString(THEME_STATE, state)
        editor.apply()
    }
}