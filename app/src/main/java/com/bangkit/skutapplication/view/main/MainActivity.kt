package com.bangkit.skutapplication.view.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivityMainBinding
import com.bangkit.skutapplication.view.home.HomeFragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.datastore.ViewModelFactory
import com.bangkit.skutapplication.view.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        val navView: BottomNavigationView = binding.bottomNavigationView

        val navController = findNavController(R.id.navHostFragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
               R.id.homeNav, R.id.cameraNav, R.id.profileNav
            )
        )

//        setupViewModel()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.cameraNav) {

                navView.visibility = View.GONE
            } else {

                navView.visibility = View.VISIBLE
            }
        }

//        selection()
    }

    private fun setupViewModel() {
        mainViewModel.getUser().observe(this) { user ->
            if (user.token.isNotEmpty()) {
//                binding.nameTextView.text = getString(R.string.greeting, user.name)
                Log.d("token", user.token)

//                mainViewModel.getUserStories(user.token)
//
//                mainViewModel.isError.observe(this) {
//                    showMessage(it)
//                }
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

//    private fun selection() {
//        loadFragment(HomeFragment())
//        binding.bottomNavigationView.setOnItemReselectedListener {
//            when (it.itemId) {
//                R.id.home -> {
//                    loadFragment(HomeFragment())
//                }
//                R.id.camera -> {
//                    loadFragment(ChatFragment())
//                    return@setOnNavigationItemReselectedListener
//                }
//                R.id.profile -> {
//                    loadFragment(SettingFragment())
//                    return@setOnNavigationItemReselectedListener
//                }
//            }
//        }
//    }
//    private fun loadFragment(fragment: Fragment){
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.container,fragment)
//        transaction.addToBackStack(null)
//        transaction.commit()
//    }

}