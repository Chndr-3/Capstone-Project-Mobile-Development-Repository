package com.bangkit.skutapplication.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.model.User
import kotlinx.coroutines.launch

class ProfileViewModel(private val pref: UserPreference): ViewModel() {
    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}