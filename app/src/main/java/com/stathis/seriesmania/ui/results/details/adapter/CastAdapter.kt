package com.stathis.seriesmania.ui.results.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.cast.Cast
import com.stathis.seriesmania.base.BaseDiffUtil
import com.stathis.seriesmania.base.BaseViewHolder
import com.stathis.seriesmania.databinding.HolderCastItemBinding

class CastAdapter(
    private val callback: CastCallback
) : ListAdapter<UiModel, CastViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = HolderCastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CastViewHolder(
    private val binding: HolderCastItemBinding,
    private val callback: CastCallback
) : BaseViewHolder(binding) {

    override fun present(data: UiModel) {
        when (data) {
            is Cast -> {
                binding.model = data
                binding.callback = callback
            }
        }
    }
}

fun interface CastCallback {
    fun onActorClick(actor: Cast)
}