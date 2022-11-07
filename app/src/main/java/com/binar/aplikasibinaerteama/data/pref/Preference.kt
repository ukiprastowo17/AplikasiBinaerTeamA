package com.binar.aplikasibinaerteama.data.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.binar.aplikasibinaerteama.constant.CommonConstant


class Preference(context: Context) {

    private val preference: SharedPreferences =
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    companion object {
        private const val NAME = CommonConstant.PREF_NAME
    }


    fun isSkipIntro(): Boolean {
        return preference.getBoolean(
            PreferenceKey.PREF_IS_SKIP_INTRO.first,
            PreferenceKey.PREF_IS_SKIP_INTRO.second
        )
    }

    fun setSkipIntro(isSkipIntro: Boolean) {
        preference.edit {
            this.putBoolean(PreferenceKey.PREF_IS_SKIP_INTRO.first, isSkipIntro)
        }
    }

    fun getToken(): String? {
        return preference.getString(
            PreferenceKey.PREF_TOKEN.first,
            PreferenceKey.PREF_TOKEN.second)
    }

}

object PreferenceKey {
    val PREF_IS_SKIP_INTRO = Pair("PREF_IS_SKIP_INTRO", false)
    val PREF_TOKEN = Pair("PREF_TOKEN", null)

}