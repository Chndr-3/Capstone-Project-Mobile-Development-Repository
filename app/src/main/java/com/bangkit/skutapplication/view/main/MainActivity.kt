package com.bangkit.skutapplication.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivityMainBinding
import com.bangkit.skutapplication.view.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        selection()
    }

    private fun selection() {
        loadFragment(HomeFragment())
        binding.bottomNavigationView.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                }
//                R.id.camera -> {
//                    loadFragment(ChatFragment())
//                    return@setOnNavigationItemReselectedListener
//                }
//                R.id.profile -> {
//                    loadFragment(SettingFragment())
//                    return@setOnNavigationItemReselectedListener
//                }
            }
        }
    }
    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}