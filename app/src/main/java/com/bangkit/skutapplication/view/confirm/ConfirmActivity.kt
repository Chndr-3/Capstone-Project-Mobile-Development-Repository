package com.bangkit.skutapplication.view.confirm

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivityConfirmBinding
import com.bangkit.skutapplication.view.camera.CameraFragment
import com.google.android.material.appbar.MaterialToolbar
import java.io.File

class ConfirmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmBinding

    lateinit var imageUri: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: MaterialToolbar = binding.toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.title = getString(R.string.confirm)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.retakeButton.setOnClickListener {
            val intent = Intent(this, CameraFragment::class.java)
            startActivity(intent)
        }

        imageUri = intent.extras?.getString(EXTRA_IMAGE_URI).toString()
        binding.imgPreview.setImageURI(Uri.parse(imageUri))


    }

    private var getFile: File? = null
//    private val launcherIntentCameraX = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) {
//        if (it.resultCode == CAMERA_X_RESULT) {
//            val myFile = it.data?.getSerializableExtra("picture") as File
//            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
//            getFile = myFile
//            val result = rotateBitmap(
//                BitmapFactory.decodeFile(myFile.path),
//                isBackCamera
//            )
//
//            binding.imgPreview.setImageBitmap(result)
//        }
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        const val EXTRA_IMAGE_URI = "extra_image"
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}