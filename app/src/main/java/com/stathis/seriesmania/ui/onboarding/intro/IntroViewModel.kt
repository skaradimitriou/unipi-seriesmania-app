package com.stathis.seriesmania.ui.onboarding.intro

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.domain.usecases.onboarding.CheckIfUserActiveUseCase
import com.stathis.seriesmania.base.BaseViewModel
import com.stathis.seriesmania.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val useCase: CheckIfUserActiveUseCase
) : BaseViewModel(app) {

    val userIsLoggedIn: LiveData<Boolean>
        get() = _userIsLoggedIn

    private val _userIsLoggedIn = MutableLiveData<Boolean>()

    fun getCurrentUser() {
        viewModelScope.launch(dispatcher) {
            _userIsLoggedIn.postValue(useCase.invoke())
        }
    }
}