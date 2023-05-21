package com.stathis.seriesmania.ui.results.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.util.decorations.HorizontalItemDecoration
import com.stathis.core.util.decorations.VerticalItemDecoration
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.details.CastModel
import com.stathis.domain.model.details.ReviewsModel
import com.stathis.seriesmania.BR
import com.stathis.seriesmania.R
import com.stathis.seriesmania.base.BaseDiffUtil
import com.stathis.seriesmania.base.BaseViewHolder
import com.stathis.seriesmania.databinding.HolderCastParentBinding
import com.stathis.seriesmania.databinding.HolderDetailsHeaderBinding
import com.stathis.seriesmania.databinding.HolderEmptyViewBinding
import com.stathis.seriesmania.databinding.HolderReviewParentBinding

class DetailsAdapter() : ListAdapter<UiModel, DetailsViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            R.layout.holder_details_header -> {
                HolderDetailsHeaderBinding.inflate(inflater, parent, false)
            }
            R.layout.holder_cast_parent -> {
                HolderCastParentBinding.inflate(inflater, parent, false)
            }
            R.layout.holder_review_parent -> {
                HolderReviewParentBinding.inflate(inflater, parent, false)
            }
            else -> HolderEmptyViewBinding.inflate(inflater, parent, false)
        }
        return DetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is TvSeries -> R.layout.holder_details_header
        is CastModel -> R.layout.holder_cast_parent
        is ReviewsModel -> R.layout.holder_review_parent
        else -> R.layout.holder_empty_view
    }
}

class DetailsViewHolder(private val binding: ViewDataBinding) : BaseViewHolder(binding) {

    override fun present(data: UiModel) {
        when (data) {
            is TvSeries -> binding.setVariable(BR.model, data)
            is CastModel -> {
                val adapter = CastAdapter()
                val decor = HorizontalItemDecoration(20)
                adapter.submitList(data.results)
                binding.setVariable(BR.adapter, adapter)
                binding.setVariable(BR.decoration, decor)
            }
            is ReviewsModel -> {
                val adapter = ReviewsAdapter()
                val decor = VerticalItemDecoration(30)
                adapter.submitList(data.results)
                binding.setVariable(BR.adapter, adapter)
                binding.setVariable(BR.decoration, decor)
            }
            else -> Unit
        }
    }
}