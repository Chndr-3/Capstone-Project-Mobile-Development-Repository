package com.bangkit.skutapplication.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.model.User

class ProfileViewModel(private val pref: UserPreference): ViewModel() {
    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }
}