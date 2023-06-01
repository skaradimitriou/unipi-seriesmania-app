package com.stathis.seriesmania.ui.onboarding.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.base.BaseViewModel
import com.stathis.core.util.GENERIC_ERROR
import com.stathis.domain.model.Result
import com.stathis.domain.usecases.onboarding.PerformLoginUseCase
import com.stathis.seriesmania.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val loginUseCase: PerformLoginUseCase
) : BaseViewModel(app) {

    /**
     * LiveData that holds the Result of the login FirebaseAuth transaction.
     */

    val loginResult: LiveData<Result<Boolean>>
        get() = _loginResult

    private val _loginResult = MutableLiveData<Result<Boolean>>()

    fun login(email: String, pass: String) {
        viewModelScope.launch(dispatcher) {
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                val result = loginUseCase.invoke(email, pass)
                _loginResult.postValue(result)
            } else {
                _loginResult.postValue(Result.Failure(GENERIC_ERROR))
            }
        }
    }
}