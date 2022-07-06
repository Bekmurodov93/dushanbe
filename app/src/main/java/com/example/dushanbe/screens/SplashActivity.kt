package com.example.dushanbe.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.dushanbe.R
import com.example.dushanbe.screens.welcome.WelcomeActivity
import com.example.dushanbe.utils.LocaleManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val homeIntent = Intent(this, MainActivity::class.java)
        lifecycleScope.launchWhenCreated {
            delay(AUTO_HIDE_DELAY_MILLIS)
            startHomeActivity(homeIntent)
        }
//        viewModel.firstAccess.observe(this) {
//            if (it != null) {
//                val homeIntent = if (it) {
//                    Intent(this, WelcomeActivity::class.java)
//                } else {
//                    Intent(this, MainActivity::class.java)
//                }
//
//            }
//        }
    }

    companion object {
        private const val AUTO_HIDE_DELAY_MILLIS = 1000L
    }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { LocaleManager.setLocale(it) })
    }

    private fun startHomeActivity(homeIntent: Intent) {
        startActivity(homeIntent)
        finish()
    }
}