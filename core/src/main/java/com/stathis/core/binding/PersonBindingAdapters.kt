package com.stathis.core.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.stathis.core.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@BindingAdapter("popularity")
fun TextView.setPopularity(popularity: Double?) {
    val finalPopularity = ((popularity?.roundToInt() ?: 0) * 1.0).toString()
    text = String.format(resources.getString(R.string.popularity_placeholder), finalPopularity)
}

@BindingAdapter("birthday")
fun TextView.setBirthday(birthDate: String) {
    text = try {
        val inputDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputDate.parse(birthDate)
        val outputDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        outputDate.format(date)
    } catch (e: Exception) {
        birthDate.ifEmpty { resources.getString(R.string.not_found) }
    }
}

@BindingAdapter("placeOfBirth")
fun TextView.setPlaceOfBirth(placeOfBirth: String) {
    text = placeOfBirth.ifEmpty { resources.getString(R.string.not_found) }
}

@BindingAdapter("bio")
fun TextView.setBio(bio: String) {
    text = if (bio.isNotEmpty()) {
        String.format(resources.getString(R.string.bio_placeholder), bio)
    } else {
        resources.getString(R.string.empty_bio)
    }
}