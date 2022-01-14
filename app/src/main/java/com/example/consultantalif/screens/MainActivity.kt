package com.example.consultantalif.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.consultantalif.R
import com.example.consultantalif.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
//    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
//        appBarConfiguration = AppBarConfiguration(navController.graph)
        // Set up ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        viewModel.authToken.observe(this) {
            if (it.isNullOrEmpty()){
                navController.navigate(R.id.action_global_toLogin)
            }
        }
    }
}