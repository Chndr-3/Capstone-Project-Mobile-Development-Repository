package com.bangkit.skutapplication.view.disease

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        setData()
    }

    fun setData(){
        val data = intent.getParcelableExtra<ViewPagerItem>("LIST")
        binding.diseaseDetailName.text = data?.diseaseName
        binding.diseaseDetailDescription.text = data?.diseaseDescription


    }
}