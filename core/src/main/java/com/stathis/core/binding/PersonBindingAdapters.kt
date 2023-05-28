package com.stathis.core.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.stathis.core.R
import java.util.*

@BindingAdapter("knownAs")
fun TextView.setKnownAs(data: List<String>) {
    val results = data.joinToString(",").ifEmpty { "N/A" }
    text = resources.getString(R.string.also_known_as, results)
}