package com.bangkit.skutapplication.view.dailytreatment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivityAddDailyTreatmentBinding
import com.bangkit.skutapplication.helper.ItemViewModelFactory
import com.bangkit.skutapplication.model.DailyTreatmentItem

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
        takingImage()
    }

    private fun takingData(){
        binding.setButton.setOnClickListener{
            val productImage = selectedImg
            val productName = binding.productName.text
            val time = binding.timeRemainder.text
            val model = DailyTreatmentItem(0,productImage.toString(), productName.toString(), time.toString())
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
    private fun takingImage(){
        binding.productImage.setOnClickListener {
            startGallery()
        }
    }
    private fun startGallery(){
        val intent = Intent()
        intent.action = Intent.ACTION_OPEN_DOCUMENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.choose_pictures))
        launcherIntentGallery.launch(chooser)
    }
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            selectedImg = result.data?.data as Uri
            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            selectedImg.let { contentResolver.takePersistableUriPermission(it, takeFlags) }
            binding.productImage.setImageURI(selectedImg)
        }
    }
    private fun obtainViewModel(activity: AppCompatActivity): AddDailyTreatmentViewModel {
        val factory = ItemViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(AddDailyTreatmentViewModel::class.java)
    }
}