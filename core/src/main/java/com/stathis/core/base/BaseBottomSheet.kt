package com.stathis.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet<VB : ViewDataBinding>(private val layoutId: Int) :
    BottomSheetDialogFragment() {

    lateinit var binding: VB

    abstract fun init()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRoot = LayoutInflater.from(requireContext()).inflate(layoutId, container, false)
        binding = DataBindingUtil.bind(viewRoot)!!
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onStop() {
        binding.unbind()
        super.onStop()
    }
}