package com.stathis.core.components

import android.os.Bundle
import android.view.View
import com.stathis.core.R
import com.stathis.core.base.BaseBottomSheet
import com.stathis.core.databinding.GenericBottomsheetTwoOptionsBinding
import com.stathis.core.ext.serializable

class GenericBottomSheet :
    BaseBottomSheet<GenericBottomsheetTwoOptionsBinding>(R.layout.generic_bottomsheet_two_options) {

    override fun init() {
        val title = arguments?.getString(TITLE_ARG) ?: ""
        val firstOption = arguments?.getString(FIRST_OPTION_ARG) ?: ""
        val secondOption = arguments?.getString(SECOND_OPTION_ARG) ?: ""
        val listener = arguments?.serializable(LISTENER_ARG) as? GenericBottomSheetListener

        binding.headerTxtView.apply {
            text = title
            visibility = if (title.isNotEmpty()) View.VISIBLE else View.GONE
        }

        binding.primaryBtn.apply {
            text = firstOption
            visibility = if (firstOption.isNotEmpty()) View.VISIBLE else View.GONE
            setOnClickListener {
                listener?.onFirstOptionClick()
                dismiss()
            }
        }

        binding.secondaryBtn.apply {
            text = secondOption
            visibility = if (secondOption.isNotEmpty()) View.VISIBLE else View.GONE
            setOnClickListener {
                listener?.onSecondOptionClick()
                dismiss()
            }
        }
    }

    data class Builder(
        private var title: String? = null,
        private var firstOption: String? = null,
        private var secondOption: String? = null,
        private var listener: GenericBottomSheetListener? = null
    ) {
        fun setTitle(title: String) = apply { this.title = title }

        fun setFirstOption(option: String) = apply { this.firstOption = option }

        fun setSecondOption(option: String) = apply { this.secondOption = option }

        fun setListener(listener: GenericBottomSheetListener) = apply { this.listener = listener }

        fun build() = GenericBottomSheet().apply {
            arguments = Bundle().apply {
                putString(TITLE_ARG, title)
                putString(FIRST_OPTION_ARG, firstOption)
                putString(SECOND_OPTION_ARG, secondOption)
                putSerializable(LISTENER_ARG, listener)
            }
        }
    }

    interface GenericBottomSheetListener : java.io.Serializable {
        fun onFirstOptionClick()
        fun onSecondOptionClick()
    }

    companion object {
        private const val TITLE_ARG = "title"
        private const val FIRST_OPTION_ARG = "first option"
        private const val SECOND_OPTION_ARG = "second option"
        private const val LISTENER_ARG = "listener arg"

        const val GENERIC_BS_TAG = "Generic BottomSheet"
    }
}