package com.stathis.core.ext

import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar

/**
 * Helper method to show a simple [Snackbar].
 */

fun ViewDataBinding.showSnackbar(message: String) {
    Snackbar.make(this.root, message, Snackbar.LENGTH_LONG).show()
}