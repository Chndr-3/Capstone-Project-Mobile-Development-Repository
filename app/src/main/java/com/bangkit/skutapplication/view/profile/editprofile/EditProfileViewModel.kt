package com.bangkit.skutapplication.view.profile.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.model.User
import kotlinx.coroutines.launch

class EditProfileViewModel(private val pref: UserPreference) : ViewModel() {
    fun saveUser(user: String, image: String) {
        viewModelScope.launch {
            pref.saveUsername(user, image)
        }
    }
    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }
}