package com.stathis.seriesmania.ui.profile.user

import android.app.Application
import com.stathis.core.base.BaseViewModel
import com.stathis.seriesmania.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,

) : BaseViewModel(app) {

}