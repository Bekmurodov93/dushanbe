package com.example.dushanbe.utils

import androidx.annotation.StringDef

object Constants {
    const val BASE_URL = "https://pregnant.imruz.com"
    const val AUTH_TOKEN = "authToken"
    const val FIRST_ACCESS = "first_access"
    const val DB_VERSION = 11
    const val DB_VERSION_NEXT = DB_VERSION+1
    const val DB_NAME = "patient"
    const val PREF_NAME = "prefs"
    const val LANGUAGE= "lang"
    const val LANGUAGE_CHANGED= "changed"
    const val dateRegex: String = "^\\d{2}\\.\\d{2}\\.\\d{4}\$"

    @StringDef(RUSSIAN, TAJIK)
    annotation class LocaleDef {
        companion object {
            var SUPPORTED_LOCALES = arrayOf(RUSSIAN, TAJIK, )
        }
    }

    const val RUSSIAN = "ru"
    const val TAJIK = "tg"
}