package com.bangkit.skutapplication.view.dailytreatment

import android.os.Bundle
import android.text.InputFilter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivityAddDailyTreatmentBinding
import com.bangkit.skutapplication.helper.ItemViewModelFactory
import com.bangkit.skutapplication.model.DailyTreatmentItem
import com.bangkit.skutapplication.view.customview.MinMaxFilter


class AddDailyTreatmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddDailyTreatmentBinding
    private lateinit var addDailyTreatmentViewModel: AddDailyTreatmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_daily_treatment)
        binding = ActivityAddDailyTreatmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addDailyTreatmentViewModel = obtainViewModel(this@AddDailyTreatmentActivity)

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
                else -> {
                    val model = DailyTreatmentItem(0, productName.toString(), time)
                    addDailyTreatmentViewModel.insert(model)
                    Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): AddDailyTreatmentViewModel {
        val factory = ItemViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(AddDailyTreatmentViewModel::class.java)
    }
}