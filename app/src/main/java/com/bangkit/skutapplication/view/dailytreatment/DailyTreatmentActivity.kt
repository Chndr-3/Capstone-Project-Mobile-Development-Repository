package com.bangkit.skutapplication.view.dailytreatment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivityDailyTreatmentBinding
import com.bangkit.skutapplication.helper.ItemViewModelFactory

class DailyTreatmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDailyTreatmentBinding
    private lateinit var dailyTreatmentViewModel: DailyTreatmentViewModel
    private lateinit var adapter: DailyTreatmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_treatment)
        binding = ActivityDailyTreatmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dailyTreatmentViewModel = obtainViewModel(this@DailyTreatmentActivity)
        binding.floatingActionButton.setOnClickListener{
            intent = Intent(this, AddDailyTreatmentActivity::class.java)
            startActivity(intent)

        }
        adapter = DailyTreatmentAdapter()
        binding.rvItem?.layoutManager = LinearLayoutManager(this)
        binding.rvItem?.setHasFixedSize(true)
        binding.rvItem?.adapter = adapter
        dailyTreatmentViewModel.getAllItem().observe(this){
            if (it != null) {
                adapter.setListItem(it)
            }
        }

    }
    private fun obtainViewModel(activity: AppCompatActivity): DailyTreatmentViewModel {
        val factory = ItemViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DailyTreatmentViewModel::class.java)
    }
}