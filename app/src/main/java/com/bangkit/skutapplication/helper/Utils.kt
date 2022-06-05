package com.bangkit.skutapplication.helper

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import com.bangkit.skutapplication.R
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

@Throws(IOException::class)
fun rotateImageIfRequired(img: Bitmap, selectedImage: Uri): Bitmap {
    val ei = ExifInterface(selectedImage.path!!)
    return when (ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270)
        else -> img
    }
}

fun rotateImage(img: Bitmap, degree: Int): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degree.toFloat())
    val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
    img.recycle()
    return rotatedImg
}

fun flip(src: Bitmap): Bitmap {
    // create new matrix for transformation
    val matrix = Matrix()
    matrix.preScale(1.0f, -1.0f)

    // return transformed image
    return Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
}

//fun reduceFileImage(file: File): File {
//    val bitmap = BitmapFactory.decodeFile(file.path)
//    var compressQuality = 100
//    var streamLength: Int
//    do {
//        val bmpStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
//        val bmpPicByteArray = bmpStream.toByteArray()
//        streamLength = bmpPicByteArray.size
//        compressQuality -= 5
//    } while (streamLength > 1000000)
//    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
//    return file
//}