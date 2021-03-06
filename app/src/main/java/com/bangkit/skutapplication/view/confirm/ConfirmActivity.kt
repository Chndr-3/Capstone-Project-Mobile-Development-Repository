package com.bangkit.skutapplication.view.confirm

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivityConfirmBinding
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.datastore.ViewModelFactory
import com.bangkit.skutapplication.model.response.UploadResponse
import com.bangkit.skutapplication.view.login.LoginActivity
import com.bangkit.skutapplication.view.result.ResultActivity
import com.google.android.material.appbar.MaterialToolbar
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ConfirmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmBinding
    private val confirmViewModel: ConfirmViewModel by viewModels()

    private lateinit var imageUri: String

    private var pressed = false

    private lateinit var scanResult: UploadResponse

    @RequiresApi(Build.VERSION_CODES.N)
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
        imageUri = intent.extras?.getString(EXTRA_IMAGE_URI).toString()

        val imageStream = contentResolver.openInputStream(Uri.parse(imageUri))
        val bitmapImage = BitmapFactory.decodeStream(imageStream)

        Log.d("imaggeeee", imageUri)
        Log.d("imaggeeee2", bitmapImage.toString())

        val rotatedBitmap = rotateImageIfRequired(bitmapImage)
        binding.imgPreview.setImageBitmap(Bitmap.createScaledBitmap(rotatedBitmap,1000,1400,false))

        Log.d("test", imageUri)

        binding.mirrorButton.setOnClickListener {
            pressed = if (pressed) {
                binding.imgPreview.setImageBitmap(flip2(rotatedBitmap))
                false
            } else {
                binding.imgPreview.setImageBitmap(flip(rotatedBitmap))
                true
            }


        }

        binding.uploadButton.setOnClickListener {
            if (pressed) {
                confirmViewModel.setImageBase64(bitmapToBase64(flip(rotatedBitmap)))
            } else {
                confirmViewModel.setImageBase64(bitmapToBase64(flip2(rotatedBitmap)))
            }
            confirmViewModel.getUser().observe(this) { user ->

                if (user.token.isNotEmpty()) {
                    Log.d("token", user.token)

                    uploadImage(user.token)

                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }

        confirmViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        confirmViewModel.isError.observe(this) {
            showMessage(it)
        }

        confirmViewModel.scanResult.observe(this) { scanResult1 ->
            scanResult = UploadResponse(scanResult1.imgLink, scanResult1.user, scanResult1.scanResult)
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap?): String {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        val byteArray: ByteArray = stream.toByteArray()

        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun uploadImage(token: String) {

        confirmViewModel.uploadImage(token)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun rotateImageIfRequired(img: Bitmap): Bitmap {
        val selectedImage = contentResolver.openInputStream(Uri.parse(imageUri))
        val ei = selectedImage?.let { ExifInterface(it) }

        return when (ei?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270)
            else -> img
        }
    }

    private fun rotateImage(img: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
        img.recycle()
        return rotatedImg
    }

    private fun flip(src: Bitmap): Bitmap {
        // create new matrix for transformation
        val matrix = Matrix()
        matrix.preScale(-1.0f, 1.0f)

        // return transformed image
        return Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
    }

    private fun flip2(src: Bitmap): Bitmap {
        // create new matrix for transformation
        val matrix = Matrix()
        matrix.preScale(1.0f, 1.0f)

        // return transformed image
        return Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
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
            Toast.makeText(this@ConfirmActivity, getString(R.string.upload_failed), Toast.LENGTH_SHORT).show()
        } else {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.success))
                setMessage(getString(R.string.please_continue))
                setPositiveButton("Lanjut") { _, _ ->


                    val intent = Intent(context, ResultActivity::class.java)
                    intent.putExtra("extra_data", scanResult)
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
        const val EXTRA_IMAGE_URI = "extra_image"
    }
}