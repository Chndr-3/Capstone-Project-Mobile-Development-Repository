package com.bangkit.skutapplication.view.history

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.skutapplication.databinding.ActivityFaceScanHistoryBinding
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.datastore.ViewModelFactory
import com.bangkit.skutapplication.model.response.ListHistoryFaceItem
import com.bangkit.skutapplication.view.home.HomeViewModel
import java.util.ArrayList

//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class FaceScanHistoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFaceScanHistoryBinding
    private var data : ArrayList<ListHistoryFaceItem>? = arrayListOf()
//    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaceScanHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val homeViewModel = ViewModelProvider(
//            this,
//            ViewModelFactory(UserPreference.getInstance(dataStore))
//        )[HomeViewModel::class.java]

//        val data = intent.getParcelableArrayListExtra<ListHistoryFaceItem>("historyData")
//        setData(data)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (data!!.isEmpty()) {
            data = intent.getParcelableArrayListExtra<ListHistoryFaceItem>("historyData")
            setData(data)
        }else{
            data!!.clear()
        }
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