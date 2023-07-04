package com.stathis.seriesmania.ui.profile.otheruser

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.base.BaseViewModel
import com.stathis.core.ext.toNotNull
import com.stathis.domain.combiners.UserProfileCombiner
import com.stathis.domain.model.Result
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.User
import com.stathis.domain.usecases.follow.FetchIfIFollowThisUserUseCase
import com.stathis.domain.usecases.follow.FollowUserUseCase
import com.stathis.domain.usecases.follow.UnfollowUserUseCase
import com.stathis.seriesmania.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val combiner: UserProfileCombiner,
    private val followUserUseCase: FollowUserUseCase,
    private val unfollowUserUseCase: UnfollowUserUseCase,
    private val doIFollowThisUserUseCase: FetchIfIFollowThisUserUseCase
) : BaseViewModel(app) {

    val userInfo: LiveData<Result<List<UiModel>>>
        get() = _userInfo

    private val _userInfo = MutableLiveData<Result<List<UiModel>>>()

    val isFollow: LiveData<Result<Boolean>>
        get() = _isFollow

    private val _isFollow = MutableLiveData<Result<Boolean>>(Result.Success(false))

    private var _followState = false
    private var _currentUser: User? = null

    init {
        fetchMyFollowList()
    }

    fun getProfileInfo(user: User) {
        _userInfo.postValue(Result.Loading())
        _currentUser = user
        viewModelScope.launch(dispatcher) {
            val result = combiner.invoke(user.id)
            _userInfo.postValue(Result.Success(result))
        }
    }

    private fun fetchMyFollowList() {
        viewModelScope.launch(dispatcher) {
            val isFollow = doIFollowThisUserUseCase.invoke(_currentUser?.id.toNotNull())
            _followState = isFollow
            _isFollow.postValue(Result.Success(isFollow))
        }
    }

    fun followIconClicked() {
        _isFollow.postValue(Result.Loading())
        viewModelScope.launch(dispatcher) {
            if (_followState) {
                unfollowUserUseCase.invoke(_currentUser?.id.toNotNull())
                fetchMyFollowList()
            } else {
                followUserUseCase.invoke(_currentUser?.id.toNotNull())
                fetchMyFollowList()
            }
        }
    }
}