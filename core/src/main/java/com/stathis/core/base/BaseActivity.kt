package com.stathis.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VB : ViewDataBinding>(private val layoutId: Int) : AppCompatActivity() {

    lateinit var binding: VB
    abstract fun init()
    abstract fun startOps()
    abstract fun stopOps()

    abstract fun showLoader()
    abstract fun hideLoader()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        init()
    }

    override fun onPostResume() {
        super.onPostResume()
        startOps()
    }

    override fun onStop() {
        stopOps()
        super.onStop()
    }
}