package com.example.dushanbe.screens

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.dushanbe.R
import com.example.dushanbe.databinding.ActivityMainBinding
import com.example.dushanbe.utils.LocaleManager
import com.example.dushanbe.utils.ui.invisible
import com.example.dushanbe.utils.ui.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.firstAccess.observe(this) {
            if (it != null) {
                if (it) {
                    navController.navigate(R.id.action_global_toLanguage)
                }
            }
        }

        binding.engWrapper.setOnClickListener { setLocale("tg") }
        binding.ruWrapper.setOnClickListener { setLocale("ru") }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.languageFragment) {
                binding.logoWrapper.invisible()
                binding.langSelector.invisible()
            } else {
                binding.logoWrapper.visible()
                binding.langSelector.visible()
            }
        }

    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.languageFragment) {
            if (!viewModel.firstAccess.value!!) {
                return
            }
        }

        if (navController.currentDestination?.id == R.id.homeFragment) return
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { LocaleManager.setLocale(it) })
    }

    fun setLocale(language: String) {
        viewModel.persistLanguage(language)
        LocaleManager.setNewLocale(this, language)
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }
}