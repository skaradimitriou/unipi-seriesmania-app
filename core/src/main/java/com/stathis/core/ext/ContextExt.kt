package com.stathis.core.ext

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.stathis.core.databinding.AskUserBottomsheetBinding

/**
 * Helper fun to get colors from resources
 */

fun Context.getActualColor(color: Int): Int = ContextCompat.getColor(this, color)

fun Context.askUserForAction(title: String, btnTitle: String, onPrimaryBtnClick: () -> Unit) {
    val binding = AskUserBottomsheetBinding.inflate(LayoutInflater.from(this))
    BottomSheetDialog(this).apply {
        setContentView(binding.root)
        binding.headerTxtView.text = title
        binding.primaryBtn.text = btnTitle
        binding.primaryBtn.setOnClickListener {
            onPrimaryBtnClick.invoke()
            dismiss()
        }

        binding.secondaryBtn.setOnClickListener {
            dismiss()
        }
    }.show()
}

/**
 * Helper method to get a drawable from resources.
 */

fun Context.getAppDrawable(drawable: Int): Drawable? {
    return ResourcesCompat.getDrawable(resources, drawable, null)
}