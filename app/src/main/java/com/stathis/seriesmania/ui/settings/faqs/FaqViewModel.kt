package com.stathis.seriesmania.ui.settings.faqs

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.base.BaseViewModel
import com.stathis.domain.model.faq.Faq
import com.stathis.domain.usecases.settings.FetchFaqsUseCase
import com.stathis.seriesmania.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FaqViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val fetchFaqsUseCase: FetchFaqsUseCase
) : BaseViewModel(app) {

    val faqs: LiveData<List<Faq>>
        get() = _faqs

    private val _faqs = MutableLiveData<List<Faq>>()

    fun fetchFaqs() {
        viewModelScope.launch(dispatcher) {
            fetchFaqsUseCase.invoke().collect {
                _faqs.postValue(it)
            }
        }
    }
}