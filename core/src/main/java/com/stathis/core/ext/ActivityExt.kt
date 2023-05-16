package com.stathis.core.ext

import android.app.Activity
import android.graphics.Bitmap
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.hideActionBar() = supportActionBar?.hide()

/**
 * Helper fun to simplify a successful camera result intent
 */

fun AppCompatActivity.onSuccessCameraResult(data: (Bitmap?) -> Unit) = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
) { result ->
    if (result.resultCode == Activity.RESULT_OK) {
        val bitmap = result.data?.extras?.get("data") as? Bitmap
        data.invoke(bitmap)
    }
}