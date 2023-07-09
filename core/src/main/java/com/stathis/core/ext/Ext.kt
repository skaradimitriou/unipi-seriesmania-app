package com.stathis.core.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.stathis.core.R
import java.io.ByteArrayOutputStream

/**
 * Helper method to show a simple [Snackbar].
 */

fun ViewDataBinding.showSnackbar(message: String) {
    Snackbar.make(this.root, message, Snackbar.LENGTH_LONG).show()
}

fun NavController.init(navigationId: Int, destinationId: Int) {
    val navGraph = navInflater.inflate(navigationId)
    navGraph.setStartDestination(destinationId)
    graph = navGraph
}

fun <T : RecyclerView> T.removeItemDecorations() {
    while (itemDecorationCount > 0) {
        removeItemDecorationAt(0)
    }
}

/**
 * Helper method to get the appropriate favorite icon drawable id (either white or red
 */

fun getAppropriateIcon(isFavorite: Boolean): Int {
    return if (isFavorite) {
        R.drawable.ic_bookmark_yellow
    } else {
        R.drawable.ic_bookmark_white
    }
}

/**
 * Helper fun to get a menu item in a safe way
 */

fun Menu.getItemOrNull(position: Int): MenuItem? {
    return try {
        getItem(position)
    } catch (e: Exception) {
        null
    }
}

/**
 * Helper fun to compress a Bitmap.
 */

fun Bitmap.compressBitmap(): ByteArray {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    return baos.toByteArray()
}

fun Uri.toBitmap(context: Context): Bitmap {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, this))
    } else {
        MediaStore.Images.Media.getBitmap(context.contentResolver, this)
    }
}