package com.stathis.core.ext

import android.graphics.Bitmap
import android.view.Menu
import android.view.MenuItem
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
        R.drawable.ic_favorite_red
    } else {
        R.drawable.ic_favorite_white
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