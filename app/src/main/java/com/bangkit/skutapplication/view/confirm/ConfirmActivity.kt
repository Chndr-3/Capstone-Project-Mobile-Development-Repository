package com.bangkit.skutapplication.view.confirm

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivityConfirmBinding
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.datastore.ViewModelFactory
import com.bangkit.skutapplication.helper.rotateImageIfRequired
import com.bangkit.skutapplication.view.result.ResultActivity
import com.google.android.material.appbar.MaterialToolbar
import java.io.ByteArrayOutputStream

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ConfirmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmBinding
    private val confirmViewModel: ConfirmViewModel by viewModels()

    private lateinit var imageUri: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val confirmViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ConfirmViewModel::class.java]

        val toolbar: MaterialToolbar = binding.toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.title = getString(R.string.confirm)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.retakeButton.setOnClickListener { onBackPressed() }

        imageUri = intent.extras?.getString(EXTRA_IMAGE_URI).toString()

        val imageStream = contentResolver.openInputStream(Uri.parse(imageUri))
        val bitmapImage = BitmapFactory.decodeStream(imageStream)
        val rotatedBitmap = rotateImageIfRequired(bitmapImage, Uri.parse(imageUri))


//        binding.imgPreview.setImageURI(Uri.parse(imageUri))
        binding.imgPreview.setImageBitmap(rotatedBitmap)

        Log.d("test", imageUri)

        binding.uploadButton.setOnClickListener {
            confirmViewModel.setImageBase64(bitmapToBase64(rotatedBitmap))
            uploadImage()
        }

        confirmViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        val byteArray: ByteArray = stream.toByteArray()

        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun uploadImage() {

        confirmViewModel.uploadImage("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjIiLCJuYW1lIjoiQWJpeXl1IiwiZW1haWwiOiJtaG1kYWJpeXl1MTlAZ21haWwuY29tIiwicGFzc3dvcmQiOiIkMmIkMTAkSnhDYVRlOHVZaC5aVGtkUS5hc1p0dVRJdFFCamw2MjEwTmlUZ3NoRnguUTh4VE16YVZVZHkiLCJpYXQiOjE2NTQzNDI3NTYsImV4cCI6MTY1NjkzNDc1Nn0.2mg1uNRg8LDJ6QptaA4beCPI8i0d7kGErsa25DAxk9Q")

        confirmViewModel.isError.observe(this) {
            showMessage(it)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showMessage(isError: Boolean) {
        if (isError) {
            Toast.makeText(this@ConfirmActivity, "error", Toast.LENGTH_SHORT).show()
        } else {
            AlertDialog.Builder(this).apply {
                setTitle("Berhasil!")
                setMessage("Silahkan Lanjut")
                setPositiveButton("Lanjut") { _, _ ->
                    val intent = Intent(context, ResultActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                create()
                show()
            }
        }
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        const val EXTRA_IMAGE_URI = "extra_image"
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}