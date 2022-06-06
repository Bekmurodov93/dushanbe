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
import com.example.dushanbe.utils.ui.Utils.countryCodeToFlagEmoji
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()
    private var lang=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)
//        if (!lang){
//            setLocale(viewModel.lang!!)
//        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
//        binding.bottomNavigation.setupWithNavController(navController)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
        // Set up ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
//        viewModel.authToken.observe(this) {
//            if (it.isNullOrEmpty() && it != "default"){
//                navController.navigate(R.id.action_global_toLogin)
//            }
//        }

//        binding.ruFlag.text=countryCodeToFlagEmoji("ru")
//        binding.engFlag.text=countryCodeToFlagEmoji("en")

         viewModel.firstAccess.observe(this) {
            if (it != null) {
                 if (it) {
                     navController.navigate(R.id.action_global_toLogin)
                 }
            }
        }

    }
    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.homeFragment){
            if (!viewModel.firstAccess.value!!){
                return
            }
        }

        if (navController.currentDestination?.id == R.id.loginFragment) return
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
        LocaleManager.setNewLocale(this,language)
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }
}