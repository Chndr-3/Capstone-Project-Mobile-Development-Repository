package com.bangkit.skutapplication.view.camera

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.FragmentCameraBinding
import com.bangkit.skutapplication.view.confirm.ConfirmActivity
import com.bangkit.skutapplication.view.confirm.ConfirmActivity.Companion.EXTRA_IMAGE_URI
import com.bangkit.skutapplication.view.main.MainActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding

    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    lateinit var cameraLifecycle: Camera
//    lateinit var rotatedBitmap: Bitmap

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        if (allPermissionsGranted()) {
            startCamera()
        }
        else {
            Toast.makeText(
                requireContext(),
                getString(R.string.permission_not_granted),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            permissionLauncher.launch(REQUIRED_PERMISSIONS)
        }

        binding.captureImage.setOnClickListener {
            takePhoto()
        }

        binding.galleryBtn.setOnClickListener {
            launcherIntentGallery.launch("image/*")
        }

        binding.switchCamera.setOnClickListener {
            cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
            else CameraSelector.DEFAULT_BACK_CAMERA

            startCamera()
        }

        binding.backButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()

        createNewPopupDialog()

    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = File(outputDirectory, SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg")

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.failed_take_photo),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    Log.d(TAG, msg)
                    Log.d("tes", savedUri.toString())
//                    Toast.makeText(safeContext, msg, Toast.LENGTH_SHORT).show()

                    val intent = Intent(activity, ConfirmActivity::class.java)
                    intent.putExtra(EXTRA_IMAGE_URI, savedUri.toString())
                    startActivity(intent)
                }
            }
        )
    }

    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraLifecycle = cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
                zoomCamera(cameraLifecycle, binding.viewFinder)

            } catch (exc: Exception) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.failed_to_load_camera),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    override fun onResume() {
        super.onResume()
        startCamera()
    }

    private fun getOutputDirectory(): File {
        val mediaDir = activity?.externalMediaDirs?.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else activity?.filesDir!!
    }


    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { result ->
        if (result != null) {

            val selectedImg: Uri = result

            val intent = Intent(activity, ConfirmActivity::class.java)
            intent.putExtra(EXTRA_IMAGE_URI, selectedImg.toString())
            startActivity(intent)
            Log.d("imageeeeee", selectedImg.toString())
        }
    }

    private fun createNewPopupDialog() {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(requireContext())
        // set the custom layout
        val view = layoutInflater.inflate(R.layout.popup_layout, null)

//        val tvTitle: TextView = view.findViewById(R.id.tvTitle) // Get reference of your XML views

        val btnClose: Button = view.findViewById(R.id.btnClose)

        btnClose.setOnClickListener {
            dialog?.dismiss()
        }

        builder.setView(view)
        // create and show the alert dialog
        dialog = builder.create()
        dialog.show()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun zoomCamera(cameraLifecycle: Camera, viewFinder: PreviewView) {
        val listener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                val currentZoomRatio: Float = cameraLifecycle.cameraInfo.zoomState.value?.zoomRatio ?: 1F
                val delta = detector.scaleFactor
                cameraLifecycle.cameraControl.setZoomRatio(currentZoomRatio * delta)
                return true
            }
        }
        val scaleGestureDetector = ScaleGestureDetector(context, listener)

        viewFinder.setOnTouchListener { _: View, motionEvent: MotionEvent ->
            scaleGestureDetector.onTouchEvent(motionEvent)
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    // Get the MeteringPointFactory from PreviewView
                    val factory = viewFinder.meteringPointFactory

                    // Create a MeteringPoint from the tap coordinates
                    val point = factory.createPoint(motionEvent.x, motionEvent.y)

                    // Create a MeteringAction from the MeteringPoint, you can configure it to specify the metering mode
                    val action = FocusMeteringAction.Builder(point).build()

                    // Trigger the focus and metering. The method returns a ListenableFuture since the operation
                    // is asynchronous. You can use it get notified when the focus is successful or if it fails.
                    cameraLifecycle.cameraControl.startFocusAndMetering(action)

                    return@setOnTouchListener true
                }
                else -> return@setOnTouchListener false
            }
        }
    }

//    @SuppressLint("ClickableViewAccessibility")
//    private fun setupZoomAndTapToFocus(camera: Camera, viewFinder: PreviewView) {
//        val listener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
//            override fun onScale(detector: ScaleGestureDetector): Boolean {
//                val currentZoomRatio: Float = camera.cameraInfo.zoomState.value?.zoomRatio ?: 1F
//                val delta = detector.scaleFactor
//                camera.cameraControl.setZoomRatio(currentZoomRatio * delta)
//                return true
//            }
//        }
//
//        val scaleGestureDetector = ScaleGestureDetector(viewFinder.context, listener)
//
//        viewFinder.setOnTouchListener { _, event ->
//            scaleGestureDetector.onTouchEvent(event)
//            if (event.action == MotionEvent.ACTION_DOWN) {
//                val factory = viewFinder.createMeteringPointFactory(cameraSelector)
//                val point = factory.createPoint(event.x, event.y)
//                val action = FocusMeteringAction.Builder(point, FocusMeteringAction.FLAG_AF)
//                    .setAutoCancelDuration(5, TimeUnit.SECONDS)
//                    .build()
//                cameraControl.startFocusAndMetering(action)
//            }
//            true
//        }
//    }
}