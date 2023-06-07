package com.stathis.seriesmania.ui.forum.add

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.base.BaseViewModel
import com.stathis.domain.usecases.forum.AddNewThreadUseCase
import com.stathis.seriesmania.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddThreadViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val useCase: AddNewThreadUseCase
) : BaseViewModel(app) {

    val threadAdded: LiveData<Boolean>
        get() = _threadAdded

    private val _threadAdded = MutableLiveData<Boolean>()

    val validation: LiveData<Result>
        get() = _validation

    private val _validation = MutableLiveData<Result>()

    var title: String = ""
    var body: String = ""

    fun saveNewThread(title: String, body: String) {
        viewModelScope.launch(dispatcher) {
            val result = useCase.invoke(title, body)
            _threadAdded.postValue(result)
        }
    }

    fun validateInput() = when {
        title.isEmpty() || body.isEmpty() -> {
            _validation.postValue(Result(ctaEnabled = false))
        }

        else -> {
            _validation.postValue(Result(ctaEnabled = true))
        }
    }

    data class Result(
        val ctaEnabled: Boolean
    )
}