package com.example.dushanbe.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.annotation.StringDef
import com.example.dushanbe.utils.Constants.LANGUAGE
import java.util.*


object LocaleManager {
    const val RUSSIAN = "ru"
    const val TAJIK = "tg"



    /**
     * set current pref locale
     */
    fun setLocale(mContext: Context): Context {
        return updateResources(mContext, getLanguagePref(mContext))
    }

    /**
     * Set new Locale with context
     */
    fun setNewLocale(mContext: Context, @LocaleDef language: String): Context {
        setLanguagePref(mContext, language)
        return updateResources(mContext, language)
    }

    /**
     * Get saved Locale from SharedPreferences
     *
     * @param mContext current context
     * @return current locale key by default return english locale
     */
   private fun getLanguagePref(mContext: Context?): String? {
        val mPreferences = mContext?.getSharedPreferences(
            Constants.PREF_NAME, Context.MODE_PRIVATE
        )
        return mPreferences?.getString(LANGUAGE, RUSSIAN)
    }

    /**
     * set pref key
     */
    private fun setLanguagePref(mContext: Context, localeKey: String) {
        val mPreferences = mContext.getSharedPreferences(
            Constants.PREF_NAME, Context.MODE_PRIVATE
        )
        mPreferences.edit().putString(LANGUAGE, localeKey).apply()
    }

    /**
     * update resource
     */
    private fun updateResources(context: Context, language: String?): Context {
        var context: Context = context
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res: Resources = context.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        context = context.createConfigurationContext(config)
        return context
    }

    /**
     * get current locale
     */
    fun getLocale(res: Resources): Locale {
        val config: Configuration = res.configuration
        return if (Build.VERSION.SDK_INT >= 24) config.locales.get(0) else config.locale
    }

    @StringDef(RUSSIAN, TAJIK)
    annotation class LocaleDef {
        companion object {
            var SUPPORTED_LOCALES = arrayOf(RUSSIAN, TAJIK)
        }
    }
}