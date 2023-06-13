package com.stathis.seriesmania.ui.profile.user

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.base.BaseViewModel
import com.stathis.domain.combiners.UserProfileCombiner
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.OtherUser
import com.stathis.domain.model.profile.User
import com.stathis.domain.model.profile.toOtherUser
import com.stathis.domain.usecases.follow.FetchMyFollowersUseCase
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
    private val fetchMyFollowersUseCase: FetchMyFollowersUseCase
) : BaseViewModel(app) {

    val userInfo: LiveData<List<UiModel>>
        get() = _userInfo

    private val _userInfo = MutableLiveData<List<UiModel>>()

    val isFollow: LiveData<Boolean>
        get() = _isFollow

    private val _isFollow = MutableLiveData(false)

    private var _followState = false
    private var _currentUser: OtherUser? = null

    init {
        fetchMyFollowList()
    }

    fun getProfileInfo(user: User) {
        _currentUser = user.toOtherUser()
        viewModelScope.launch(dispatcher) {
            val result = combiner.invoke(user.id)
            _userInfo.postValue(result)
        }
    }

    private fun fetchMyFollowList() {
        viewModelScope.launch(dispatcher) {
            fetchMyFollowersUseCase.invoke().collect {
                val isFollow = it.contains(_currentUser)
                _followState = isFollow
                _isFollow.postValue(isFollow)
            }
        }
    }

    fun followIconClicked() {
        viewModelScope.launch(dispatcher) {
            if (_followState) {
                unfollowUserUseCase.invoke(_currentUser)
                fetchMyFollowList()
            } else {
                followUserUseCase.invoke(_currentUser)
                fetchMyFollowList()
            }
        }
    }
}