package com.bangkit.skutapplication.datastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.skutapplication.view.confirm.ConfirmViewModel
import com.bangkit.skutapplication.view.history.FaceScanViewModel
import com.bangkit.skutapplication.view.login.LoginViewModel
import com.bangkit.skutapplication.view.main.MainViewModel
import com.bangkit.skutapplication.view.profile.ProfileViewModel


class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(pref) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ConfirmViewModel::class.java) -> {
                ConfirmViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(FaceScanViewModel::class.java) -> {
                FaceScanViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}