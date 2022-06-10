package com.bangkit.skutapplication.view.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivityFaceScanHistoryBinding
import com.bangkit.skutapplication.model.BeautyTipsItem
import com.bangkit.skutapplication.model.response.ListHistoryFaceItem
import com.bangkit.skutapplication.view.beautytips.BeautyTipsAdapter
import java.util.ArrayList

class FaceScanHistoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFaceScanHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaceScanHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = intent.getParcelableArrayListExtra<ListHistoryFaceItem>("historyData")
        setData(data)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    private fun setData(item : ArrayList<ListHistoryFaceItem>?){
        val layoutManager = LinearLayoutManager(this)
        binding.rvHistory.layoutManager = layoutManager
        val listHistoryAdapter = item?.let { FaceScanHistoryAdapter(it) }
        binding.rvHistory.adapter = listHistoryAdapter

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}