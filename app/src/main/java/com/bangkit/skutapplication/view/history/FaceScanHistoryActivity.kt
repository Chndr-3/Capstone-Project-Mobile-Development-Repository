package com.bangkit.skutapplication.view.history

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivityFaceScanHistoryBinding
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.datastore.ViewModelFactory
import com.bangkit.skutapplication.model.DailyTreatmentItem
import com.bangkit.skutapplication.model.DeleteTreatment
import com.bangkit.skutapplication.model.response.ListHistoryFaceItem
import com.bangkit.skutapplication.model.user.DeleteHistory
import com.bangkit.skutapplication.view.dailytreatment.SkincareRoutineAdapter
import okhttp3.internal.notify


class FaceScanHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFaceScanHistoryBinding
    private val Context.dataStore by preferencesDataStore(name = "profile")
    private lateinit var viewModel: FaceScanViewModel
    private val listHistoryAdapter = FaceScanHistoryAdapter()
    private var array: ArrayList<ListHistoryFaceItem> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaceScanHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[FaceScanViewModel::class.java]
        val layoutManager = LinearLayoutManager(this)
        binding.rvHistory.layoutManager = layoutManager
        binding.rvHistory.adapter = listHistoryAdapter
        viewModel.getUser().observe(this) {
            viewModel.getItem("Bearer ${it.token}")
        }
        viewModel.listHistory.observe(this) {
            for (data in it) {
                array.add(ListHistoryFaceItem(data.eksim,data.normal,data.acne,data.imgLink,data.rosacea,data.scanId,data.id,data.timestamp))
            }
            listHistoryAdapter.setListItem(array)
        }
        viewModel.isLoading.observe(this) {
            isLoading(it)
        }
        viewModel.isSuccess.observe(this){
            if(!it){
                Toast.makeText(this, resources.getString(R.string.gagal_ambil_history_data), Toast.LENGTH_SHORT).show()
            }
        }
        listHistoryAdapter.setOnItemClickCallback(
            object : FaceScanHistoryAdapter.OnItemClickCallback {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onItemClicked(data: ListHistoryFaceItem) {
                    viewModel.getUser().observe(this@FaceScanHistoryActivity) {
                        viewModel.deleteHistory(
                            "Bearer ${it.token}",
                            DeleteHistory(data.scanId.toString())
                        )
                        array.removeIf { it.scanId == data.scanId }
                        listHistoryAdapter.setListItem(array)
                    }
                }
            }
        )
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun isLoading(bol : Boolean){
        if(bol){
            binding.progressBarHistory.visibility = View.VISIBLE
        }else{
            binding.progressBarHistory.visibility = View.GONE
        }
    }
}