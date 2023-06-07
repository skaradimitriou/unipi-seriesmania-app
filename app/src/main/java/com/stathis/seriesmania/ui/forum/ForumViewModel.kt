package com.stathis.seriesmania.ui.forum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stathis.seriesmania.ui.forum.navigator.ForumAction

class ForumViewModel : ViewModel() {

    val navigatorState: LiveData<ForumAction?>
        get() = _navigatorState

    private val _navigatorState = MutableLiveData<ForumAction?>()

    fun resetNavigation() = _navigatorState.postValue(null)

    fun navigateToScreen(action: ForumAction?) {
        _navigatorState.postValue(action)
    }
}