package com.stathis.core.components

import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import com.stathis.core.R
import com.stathis.core.base.BaseBottomSheet
import com.stathis.core.databinding.GenericRatingBottomsheetBinding
import com.stathis.core.ext.serializable

class GenericRatingBottomSheet :
    BaseBottomSheet<GenericRatingBottomsheetBinding>(R.layout.generic_rating_bottomsheet) {

    override fun init() {
        val title = arguments?.getString(TITLE_ARG) ?: ""
        val description = arguments?.getString(DESC_ARG) ?: ""
        val btnText = arguments?.getString(BTN_ARG) ?: ""
        val listener = arguments?.serializable(LISTENER_ARG) as? Listener

        binding.headerTxtView.apply {
            text = title
            visibility = if (title.isNotEmpty()) View.VISIBLE else View.GONE
        }

        binding.descTxtView.apply {
            text = description
            visibility = if (description.isNotEmpty()) View.VISIBLE else View.GONE
        }

        binding.ratingBar.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { _, rating, _ ->
                binding.rateBtn.isEnabled = true
            }

        binding.rateBtn.apply {
            text = btnText
            visibility = if (btnText.isNotEmpty()) View.VISIBLE else View.GONE
            setOnClickListener {
                val rating = binding.ratingBar.rating.toDouble() * 2
                listener?.onRateBtnClick(rating)
                dismiss()
            }
        }
    }

    data class Builder(
        private var title: String? = null,
        private var desc: String? = null,
        private var btn: String? = null,
        private var listener: Listener? = null
    ) {
        fun setTitle(title: String) = apply { this.title = title }

        fun setDescription(desc: String) = apply { this.desc = desc }

        fun setBtnText(btn: String) = apply { this.btn = btn }

        fun setListener(listener: Listener) = apply { this.listener = listener }

        fun build() = GenericRatingBottomSheet().apply {
            arguments = Bundle().apply {
                putString(TITLE_ARG, title)
                putString(DESC_ARG, desc)
                putString(BTN_ARG, btn)
                putSerializable(LISTENER_ARG, listener)
            }
        }
    }

    fun interface Listener : java.io.Serializable {
        fun onRateBtnClick(rating: Double)
    }

    companion object {
        private const val TITLE_ARG = "title"
        private const val DESC_ARG = "desc"
        private const val BTN_ARG = "btn"
        private const val LISTENER_ARG = "listener"

        const val TAG = "GenericRatingBottomSheet"
    }
}