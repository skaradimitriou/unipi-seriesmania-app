package com.stathis.seriesmania.ui.dashboard.profile

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.R
import com.stathis.core.base.BaseViewModel
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.User
import com.stathis.domain.usecases.profile.GetProfileInfoUseCase
import com.stathis.domain.usecases.profile.LogoutUserUseCase
import com.stathis.seriesmania.di.IoDispatcher
import com.stathis.seriesmania.ui.dashboard.profile.uimodel.ProfileHeader
import com.stathis.seriesmania.ui.dashboard.profile.uimodel.ProfileLogoutOption
import com.stathis.seriesmania.ui.dashboard.profile.uimodel.ProfileOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val useCase: GetProfileInfoUseCase,
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
            val result = useCase.invoke()
            _userInfo.postValue(result.toUiData())
        }
    }

    private fun User.toUiData() = listOf(
        ProfileHeader(userImg, username),
        ProfileOption(getString(R.string.email), email),
        ProfileOption(getString(R.string.telephone), telephone),
        ProfileOption(getString(R.string.first_name), firstName),
        ProfileOption(getString(R.string.last_name), lastName),
        ProfileLogoutOption(getString(R.string.logout))
    )

    fun logoutUser() {
        viewModelScope.launch(dispatcher) {
            val result = logoutUseCase.invoke()
            _userLoggedOut.postValue(result)
        }
    }
}