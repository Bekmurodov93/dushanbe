package com.example.dushanbe.screens.welcome

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dushanbe.databinding.ActivityWelcomeBinding
import com.example.dushanbe.utils.Constants
import com.example.dushanbe.utils.LocaleManager

class WelcomeActivity : AppCompatActivity() {
    private val binding: ActivityWelcomeBinding by lazy { ActivityWelcomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val prefs=getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(Constants.FIRST_ACCESS, false).apply()
    }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { LocaleManager.setLocale(it) })
    }
}