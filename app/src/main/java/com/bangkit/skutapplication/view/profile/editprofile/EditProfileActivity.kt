package com.bangkit.skutapplication.view.profile.editprofile

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivityEditProfileBinding
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.datastore.ViewModelFactory



class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private val Context.dataStore by preferencesDataStore(name = "profile")
    private lateinit var viewModel : EditProfileViewModel
    private lateinit var selectedImg: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[EditProfileViewModel::class.java]
        binding.imageView.setOnClickListener {
            startGallery()
        }
        viewModel.getUser().observe(this) {
            binding.nameEditText.setText(it.username)
            if(it.image.isNotEmpty()) {
                binding.imageView.setImageURI(it.image.toUri())
            }else{
                binding.imageView.setImageResource(R.drawable.ic_baseline_image_24)
            }
            selectedImg = it.image.toUri()
        }

        binding.buttonSave.setOnClickListener {
            val username = binding.nameEditText.text.toString()
            viewModel.saveUser(username, selectedImg.toString())
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            finish()
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
            binding.imageView.setImageURI(selectedImg)
        }
    }
}