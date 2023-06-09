package com.stathis.seriesmania.ui.onboarding.register

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.base.BaseViewModel
import com.stathis.core.util.GENERIC_ERROR
import com.stathis.domain.model.Result
import com.stathis.domain.usecases.onboarding.RegisterNewUserUseCase
import com.stathis.domain.usecases.profile.CreateNewUserProfileUseCase
import com.stathis.seriesmania.di.IoDispatcher
import com.stathis.seriesmania.ui.onboarding.login.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val useCase: RegisterNewUserUseCase,
    private val profileUseCase: CreateNewUserProfileUseCase
) : BaseViewModel(app) {

    /**
     * LiveData that holds the Result of the registration FirebaseAuth transaction.
     */

    val registrationResult: LiveData<Result<Boolean>>
        get() = _registrationResult

    private val _registrationResult = MutableLiveData<Result<Boolean>>()

    val btnState: LiveData<LoginViewModel.UiState>
        get() = _btnState

    private val _btnState = MutableLiveData<LoginViewModel.UiState>()

    var email: String = ""
    var pass: String = ""
    var confPass: String = ""

    fun validateInput() = when {
        email.isEmpty() || pass.isEmpty() || confPass.isEmpty() -> {
            _btnState.postValue(LoginViewModel.UiState(btnState = false))
        }

        else -> {
            _btnState.postValue(LoginViewModel.UiState(btnState = true))
        }
    }

    fun validateData() {
        _registrationResult.postValue(Result.Loading())
        viewModelScope.launch(dispatcher) {
            val isPassValid = pass == confPass && pass.isNotEmpty() && confPass.isNotEmpty()
            if (email.isNotEmpty() && isPassValid) {
                val result = useCase.invoke(email, pass)
                _registrationResult.postValue(result)

                if (result is Result.Success) {
                    profileUseCase.invoke(email)
                }
            } else {
                _registrationResult.postValue(Result.Failure(GENERIC_ERROR))
            }
        }
    }
}