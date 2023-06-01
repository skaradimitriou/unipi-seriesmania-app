package com.stathis.seriesmania.ui.results.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.reviews.Review
import com.stathis.seriesmania.BR
import com.stathis.seriesmania.databinding.HolderReviewItemBinding

class ReviewsAdapter : ListAdapter<UiModel, ReviewsViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        val view =
            HolderReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ReviewsViewHolder(private val binding: ViewDataBinding) : BaseViewHolder(binding) {

    override fun present(data: UiModel) {
        when (data) {
            is Review -> binding.setVariable(BR.model, data)
        }
    }
}