package com.stathis.seriesmania.ui.settings.faqs

import androidx.fragment.app.viewModels
import com.stathis.core.adapters.settings.faq.FaqAdapter
import com.stathis.core.base.BaseFragment
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.util.decorations.VerticalItemDecoration
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentFaqBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FaqFragment : BaseFragment<FragmentFaqBinding>(R.layout.fragment_faq) {

    private val viewModel: FaqViewModel by viewModels()
    private val faqAdapter = FaqAdapter()

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.faq_brief_title))

        binding.faqRecycler.apply {
            addItemDecoration(VerticalItemDecoration(10))
            adapter = faqAdapter
        }

        viewModel.fetchFaqs()
    }

    override fun startOps() {
        viewModel.faqs.observe(viewLifecycleOwner) {
            faqAdapter.submitList(it)
        }
    }

    override fun stopOps() {}
}