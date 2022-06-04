package com.bangkit.skutapplication.view.dailytreatment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivityAddDailyTreatmentBinding
import com.bangkit.skutapplication.helper.ItemViewModelFactory
import com.bangkit.skutapplication.model.DailyTreatmentItem
import com.bangkit.skutapplication.view.main.MainActivity

class AddDailyTreatmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddDailyTreatmentBinding
    private lateinit var addDailyTreatmentViewModel: AddDailyTreatmentViewModel
    private lateinit var selectedImg: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_daily_treatment)
        binding = ActivityAddDailyTreatmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addDailyTreatmentViewModel = obtainViewModel(this@AddDailyTreatmentActivity)
        takingData()
    }

    private fun takingData(){
        binding.setButton.setOnClickListener{
            val productName = binding.productName.text
            val time = binding.timeRemainder.text
            val model = DailyTreatmentItem(0,productName.toString(), time.toString())
            addDailyTreatmentViewModel.insert(model)
            Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show()
            val intentBack = Intent(
                applicationContext,
                DailyTreatmentActivity::class.java
            ) //this intent will be called once the setting alaram is completes

            intentBack.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intentBack)

        }
    }
    private fun obtainViewModel(activity: AppCompatActivity): AddDailyTreatmentViewModel {
        val factory = ItemViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(AddDailyTreatmentViewModel::class.java)
    }
}