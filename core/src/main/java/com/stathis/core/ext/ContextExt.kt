package com.stathis.core.ext

import android.content.Context
import androidx.core.content.ContextCompat

/**
 * Helper fun to get colors from resources
 */

fun Context.getActualColor(color: Int): Int = ContextCompat.getColor(this, color)