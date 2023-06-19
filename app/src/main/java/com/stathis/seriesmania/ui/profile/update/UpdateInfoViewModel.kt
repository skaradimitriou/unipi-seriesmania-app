package com.stathis.seriesmania.ui.profile.update

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.base.BaseViewModel
import com.stathis.domain.usecases.profile.GetProfileInfoUseCase
import com.stathis.domain.usecases.profile.UpdateProfileInfoUseCase
import com.stathis.seriesmania.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateInfoViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val updateProfileInfoUseCase: UpdateProfileInfoUseCase
) : BaseViewModel(app) {


    val profileUpdated: LiveData<Boolean>
        get() = _profileUpdated

    private val _profileUpdated = MutableLiveData<Boolean>()

    val userInfo: LiveData<UpdateInfoModel>
        get() = _userInfo

    private val _userInfo = MutableLiveData<UpdateInfoModel>()

    val buttonState: LiveData<Boolean>
        get() = _buttonState

    private val _buttonState = MutableLiveData<Boolean>(true)


    private var tempUsername: String? = null
    private var tempBio: String? = null

    init {
        getProfileInfo()
    }

    private fun getProfileInfo() {
        viewModelScope.launch(dispatcher) {
            val result = getProfileInfoUseCase.invoke()
            val model = UpdateInfoModel(result.username, result.bio)
            _userInfo.postValue(model)
        }
    }

    fun validateInput(username: String? = null, bio: String? = null) {
        if (!username.isNullOrEmpty() && username != tempUsername) {
            tempUsername = username
        }

        if (!bio.isNullOrEmpty() && bio != tempBio) {
            tempBio = bio
        }
    }

    data class UpdateInfoModel(val username: String, val bio: String)

    fun saveUserData() {
        viewModelScope.launch(dispatcher) {
            val result = updateProfileInfoUseCase.invoke(tempUsername, tempBio)
            _profileUpdated.postValue(result)
        }
    }
}