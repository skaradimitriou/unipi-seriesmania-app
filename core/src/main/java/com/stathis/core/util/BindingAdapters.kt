package com.stathis.core.util

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.stathis.core.R
import com.stathis.domain.model.TvSeries
import kotlin.math.abs

/**
 * This file contains the binding adapters that are used across the app
 */

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

@BindingAdapter("imageUrl")
fun ImageView.imageUrl(url: String) {
    try {
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

@BindingAdapter("itemDecoration")
fun RecyclerView.setItemDecoration(decoration: RecyclerView.ItemDecoration) {
    addItemDecoration(decoration)
}

@BindingAdapter("setViewPagerPageTransformer")
fun ViewPager2.setViewPagerPageTransformer(shouldBeSet: Boolean) {
    offscreenPageLimit = 3

    val compositePageTransformer = CompositePageTransformer()
    compositePageTransformer.addTransformer(MarginPageTransformer(25))
    compositePageTransformer.addTransformer { page: View, position: Float ->
        val r = 1 - abs(position)
        page.scaleY = 0.85f + r * 0.15f
    }
    setPageTransformer(compositePageTransformer)
}