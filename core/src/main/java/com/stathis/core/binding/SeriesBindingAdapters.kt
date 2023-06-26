package com.stathis.core.binding

import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.stathis.core.R
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.TvSeriesDetails
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@BindingAdapter("seriesDescription")
fun TextView.setSeriesDescription(description: String) {
    text = description.ifEmpty { resources.getString(R.string.no_description_found) }
}

@BindingAdapter("seriesImage")
fun ImageView.setSeriesImage(series: TvSeries) {
    try {
        val url = series.poster_path.ifEmpty { series.backdrop_path }
        Glide.with(this).load("https://image.tmdb.org/t/p/w500$url")
            .placeholder(R.mipmap.seriesmania_logo).error(R.mipmap.seriesmania_logo).into(this)
    } catch (e: Exception) {
        setImageResource(R.mipmap.seriesmania_logo)
    }
}

@BindingAdapter("seriesDetailsImage")
fun ImageView.setSeriesDetailsImage(series: TvSeriesDetails) {
    try {
        val url = series.posterPath.ifEmpty { series.backdropPath }
        Glide.with(this).load("https://image.tmdb.org/t/p/w500$url")
            .placeholder(R.mipmap.seriesmania_logo).error(R.mipmap.seriesmania_logo).into(this)
    } catch (e: Exception) {
        setImageResource(R.mipmap.seriesmania_logo)
    }
}

@BindingAdapter("seriesRating")
fun RatingBar.setSeriesRating(vote_average: Double) {
    rating = vote_average.toFloat() / 2
}

@BindingAdapter("seriesRatingTxt")
fun TextView.seriesRatingTxt(vote_average: Double) {
    text = (vote_average.roundToInt() / 2.0).toString()
}

@BindingAdapter("seriesRatingTxtFull")
fun TextView.seriesRatingTxtFull(vote_average: Double) {
    text = "${(vote_average.roundToInt() / 2.0)}/5 "
}

@BindingAdapter("seriesVotes")
fun TextView.seriesVotes(votes: Int) {
    text = "$votes votes"
}

@BindingAdapter("airDate")
fun TextView.setAiringDate(date: String) {
    text = if (date.isEmpty()) {
        resources.getString(R.string.not_found)
    } else {
        val initDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        initDate?.let { formatter.format(it) }
    }
}