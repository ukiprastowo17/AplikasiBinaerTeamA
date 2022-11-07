package com.binar.aplikasibinaerteama.data.pref


interface PreferenceDataSource {
    fun isSkipIntro(): Boolean
    fun setSkipIntro(isSkipIntro: Boolean)
}

class PreferenceDataSourceImpl(private val preference: Preference) :
    PreferenceDataSource {

    override fun isSkipIntro(): Boolean {
        return preference.isSkipIntro()
    }

    override fun setSkipIntro(isSkipIntro: Boolean) {
        preference.setSkipIntro(isSkipIntro)
    }

}