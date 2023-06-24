package com.stathis.core.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.stathis.core.R
import java.util.*
import kotlin.math.roundToInt

@BindingAdapter("popularity")
fun TextView.setPopularity(popularity: Double) {
    val finalPopularity = (popularity.roundToInt() * 1.0).toString()
    text = String.format(resources.getString(R.string.popularity_placeholder), finalPopularity)
}

@BindingAdapter("birthday")
fun TextView.setBirthday(birthDate: String) {
    text = birthDate.ifEmpty { resources.getString(R.string.not_found) }
}

@BindingAdapter("placeOfBirth")
fun TextView.setPlaceOfBirth(placeOfBirth: String) {
    text = placeOfBirth.ifEmpty { resources.getString(R.string.not_found) }
}