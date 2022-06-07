package com.bangkit.skutapplication.view.disease

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivityDiseaseBinding
import com.bangkit.skutapplication.model.ViewPagerItem

class DiseaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiseaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disease)
        binding = ActivityDiseaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setData()
    }

    private fun setData(){
        val data = intent.getParcelableExtra<ViewPagerItem>("LIST")
        binding.diseaseDetailName.text = data?.diseaseName
        binding.diseaseDetailDescription.text = data?.diseaseDescription
        binding.skinDiseaseImage.setImageResource(data?.diseaseIcon!!)


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}