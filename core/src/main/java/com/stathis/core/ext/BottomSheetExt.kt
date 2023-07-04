package com.stathis.core.ext

import android.view.View
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun BottomSheetDialogFragment.setFullScreenBottomSheet(): BottomSheetDialog {
    val dialog = BottomSheetDialog(requireContext(), theme)
    dialog.setOnShowListener {
        val bottomSheetDialog = it as BottomSheetDialog
        val parentLayout =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        parentLayout?.let { view ->
            val behaviour = BottomSheetBehavior.from(view)
            val layoutParams = view.layoutParams
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            view.layoutParams = layoutParams
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }
    return dialog
}