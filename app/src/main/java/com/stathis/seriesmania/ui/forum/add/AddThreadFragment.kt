package com.stathis.seriesmania.ui.forum.add

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.setScreenTitle
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentAddNewThreadBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddThreadFragment :
    BaseFragment<FragmentAddNewThreadBinding>(R.layout.fragment_add_new_thread) {

    private val viewModel: AddThreadViewModel by viewModels()

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.new_thread_title))

        binding.threadTitleInput.doAfterTextChanged {
            viewModel.title = it.toString()
            viewModel.validateInput()
        }

        binding.threadBodyInput.doAfterTextChanged {
            viewModel.body = it.toString()
            viewModel.validateInput()
        }

        binding.addNewThreadButton.setOnClickListener {
            val title = binding.threadTitleInput.text.toString()
            val body = binding.threadBodyInput.text.toString()
            viewModel.saveNewThread(title, body)
        }
    }

    override fun startOps() {
        viewModel.validation.observe(viewLifecycleOwner) { result ->
            binding.isEnabled = result.ctaEnabled
        }

        viewModel.threadAdded.observe(viewLifecycleOwner) {
            binding.threadTitleInput.text.clear()
            binding.threadBodyInput.text.clear()
            requireActivity().finish()
        }
    }

    override fun stopOps() {}
}