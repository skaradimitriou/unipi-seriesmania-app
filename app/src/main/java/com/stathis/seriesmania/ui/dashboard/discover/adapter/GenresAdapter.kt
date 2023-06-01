package com.stathis.seriesmania.ui.dashboard.discover.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.genres.Genre
import com.stathis.seriesmania.databinding.HolderGenresItemBinding

class GenresAdapter(
    private val callback: GenreCallback
) : ListAdapter<UiModel, GenresViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val view = HolderGenresItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return GenresViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class GenresViewHolder(
    private val binding: HolderGenresItemBinding,
    private val callback: GenreCallback
) : BaseViewHolder(binding) {

    override fun present(data: UiModel) = when (data) {
        is Genre -> {
            binding.model = data
            binding.callback = callback
        }

        else -> Unit
    }
}

fun interface GenreCallback {
    fun onGenreTap(genre: Genre)
}