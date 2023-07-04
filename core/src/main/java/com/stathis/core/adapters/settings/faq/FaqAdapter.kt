package com.stathis.core.adapters.settings.faq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.core.databinding.HolderFaqItemBinding
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.faq.Faq

class FaqAdapter : ListAdapter<UiModel, FaqViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val view = HolderFaqItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FaqViewHolder(view)
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class FaqViewHolder(val binding: HolderFaqItemBinding) : BaseViewHolder(binding) {

    override fun present(data: UiModel) {
        when (data) {
            is Faq -> {
                binding.model = data
                binding.faqCard.setOnClickListener {
                    data.isExpanded = !data.isExpanded

                    if (data.isExpanded) {
                        binding.iconMoreImgView.animate().rotation(90f).start()
                        binding.faqDescTxtView.visibility = View.VISIBLE
                    } else {
                        binding.iconMoreImgView.animate().rotation(0f).start()
                        binding.faqDescTxtView.visibility = View.GONE
                    }
                }
            }
        }
    }
}