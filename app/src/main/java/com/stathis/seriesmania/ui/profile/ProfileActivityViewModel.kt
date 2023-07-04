package com.stathis.seriesmania.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stathis.core.util.auth.Authenticator
import com.stathis.seriesmania.ui.profile.navigator.ProfileAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileActivityViewModel @Inject constructor(
    private val auth: Authenticator
) : ViewModel() {

    val navigatorState: LiveData<ProfileAction?>
        get() = _navigatorState

    private val _navigatorState = MutableLiveData<ProfileAction?>()

    fun isItMe(userId: String) = auth.getActiveUserId() == userId

    fun resetNavigation() = _navigatorState.postValue(null)

    fun navigateToScreen(action: ProfileAction?) {
        _navigatorState.postValue(action)
    }
}