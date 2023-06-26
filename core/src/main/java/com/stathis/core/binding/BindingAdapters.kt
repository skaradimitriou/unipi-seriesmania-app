package com.stathis.core.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.stathis.core.R
import kotlin.math.abs

/**
 * This file contains the binding adapters that are used across the app
 */

@BindingAdapter("loadImg")
fun ImageView.setImage(url: String) {
    try {
        Glide.with(this).load(url)
            .placeholder(R.mipmap.seriesmania_logo)
            .error(R.mipmap.seriesmania_logo)
            .into(this)
    } catch (e: Exception) {
        setImageResource(R.mipmap.seriesmania_logo)
    }
}

@BindingAdapter("setProfileImage")
fun ImageView.setProfileImage(url: String?) {
    try {
        Glide.with(this).load(url)
            .placeholder(R.drawable.profile_placeholder)
            .error(R.drawable.profile_placeholder)
            .into(this)
    } catch (e: Exception) {
        setImageResource(R.drawable.profile_placeholder)
    }
}

@BindingAdapter("imageUrl")
fun ImageView.imageUrl(url: String) {
    try {
        Glide.with(this).load("https://image.tmdb.org/t/p/w500$url")
            .placeholder(R.mipmap.seriesmania_logo).error(R.mipmap.seriesmania_logo).into(this)
    } catch (e: Exception) {
        setImageResource(R.mipmap.seriesmania_logo)
    }
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