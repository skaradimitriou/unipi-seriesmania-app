package com.stathis.seriesmania.ui.profile.profile

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.base.BaseViewModel
import com.stathis.domain.combiners.ProfileCombiner
import com.stathis.domain.model.UiModel
import com.stathis.domain.usecases.profile.LogoutUserUseCase
import com.stathis.seriesmania.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val combiner: ProfileCombiner,
    private val logoutUseCase: LogoutUserUseCase
) : BaseViewModel(app) {

    val userInfo: LiveData<List<UiModel>>
        get() = _userInfo

    private val _userInfo = MutableLiveData<List<UiModel>>()

    val userLoggedOut: LiveData<Boolean>
        get() = _userLoggedOut

    private val _userLoggedOut = MutableLiveData<Boolean>()

    fun getProfileInfo() {
        viewModelScope.launch(dispatcher) {
            val result = combiner.invoke()
            _userInfo.postValue(result)
        }
    }

    fun logoutUser() {
        viewModelScope.launch(dispatcher) {
            val result = logoutUseCase.invoke()
            _userLoggedOut.postValue(result)
        }
    }
}