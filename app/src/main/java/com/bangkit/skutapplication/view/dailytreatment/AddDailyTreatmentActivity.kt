package com.bangkit.skutapplication.view.dailytreatment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivityAddDailyTreatmentBinding
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.datastore.ViewModelFactory
import com.bangkit.skutapplication.helper.ItemViewModelFactory
import com.bangkit.skutapplication.model.PostTreatment
import com.bangkit.skutapplication.view.customview.MinMaxFilter
import com.bangkit.skutapplication.view.profile.ProfileViewModel


class AddDailyTreatmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddDailyTreatmentBinding
    private lateinit var addDailyTreatmentViewModel: AddDailyTreatmentViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_daily_treatment)
        binding = ActivityAddDailyTreatmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addDailyTreatmentViewModel = obtainViewModel(this@AddDailyTreatmentActivity)
        profileViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ProfileViewModel::class.java]
        takingData()
    }

    private fun takingData() {
        binding.timeReminderHours.filters = arrayOf<InputFilter>(MinMaxFilter(0, 23))
        binding.timeReminderMinutes.filters = arrayOf<InputFilter>(MinMaxFilter(0, 59))
        binding.setButton.setOnClickListener {
            val productName = binding.productName.text
            val timeHours = binding.timeReminderHours.text
            val timeMinute = binding.timeReminderMinutes.text
            val time = "$timeHours:$timeMinute"
            when {
                productName!!.isEmpty() -> {
                    binding.productName.error = "Insert treatment name first"
                }
                timeHours!!.isEmpty() -> {
                    binding.timeReminderHours.error = "Set the hour first"
                }
                timeMinute!!.isEmpty() -> {
                    binding.timeReminderMinutes.error = "Set the minute first"
                }
                timeHours.length != 2 -> {
                    binding.timeReminderHours.error = "Time format doesn't match"
                }
                timeMinute.length != 2 -> {
                    binding.timeReminderMinutes.error = "Time format doesn't match"
                }
                else -> {
                    val model = PostTreatment(productName.toString(), time)
                    profileViewModel.getUser().observe(this) {
                        addDailyTreatmentViewModel.postSkincareRoutine(
                            "Bearer ${it.token}", model
                        )
                    }
                    addDailyTreatmentViewModel.isSuccess.observe(this){
                        if(!it){
                            Toast.makeText(this, "You need internet connection to create a new daily treatment", Toast.LENGTH_SHORT).show()
                        } else{
                            Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, DailyTreatmentActivity::class.java)
                            intent.putExtra("tab", "1")
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                        }
                    }
                    addDailyTreatmentViewModel.isLoading.observe(this){
                        if(it){
                            binding.progressBarAddSkincareRoutine.visibility = View.VISIBLE
                        }else{
                            binding.progressBarAddSkincareRoutine.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }
}

private fun obtainViewModel(activity: AppCompatActivity): AddDailyTreatmentViewModel {
    val factory = ItemViewModelFactory.getInstance(activity.application)
    return ViewModelProvider(activity, factory).get(AddDailyTreatmentViewModel::class.java)
}
