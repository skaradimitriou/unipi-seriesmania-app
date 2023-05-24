package com.stathis.core.binding

import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.stathis.core.R
import com.stathis.domain.model.TvSeries
import java.text.SimpleDateFormat
import java.util.Locale

@BindingAdapter("seriesDescription")
fun TextView.setSeriesDescription(description: String) {
    text = description.ifEmpty { resources.getString(R.string.no_description_found) }
}

@BindingAdapter("seriesImage")
fun ImageView.setSeriesImage(series: TvSeries) {
    try {
        val url = series.poster_path.ifEmpty { series.backdrop_path }
        Glide.with(this).load("https://image.tmdb.org/t/p/w500$url")
            .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(this)
    } catch (e: Exception) {
        setImageResource(R.mipmap.ic_launcher)
    }
}

@BindingAdapter("seriesRating")
fun RatingBar.setSeriesRating(vote_average: Double) {
    rating = vote_average.toFloat()
}

@BindingAdapter("airDate")
fun TextView.setAiringDate(date: String) {
    val initDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    text = initDate?.let { formatter.format(it) }
}