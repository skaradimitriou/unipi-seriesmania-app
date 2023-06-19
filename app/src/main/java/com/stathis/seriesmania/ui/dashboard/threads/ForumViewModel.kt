package com.stathis.seriesmania.ui.dashboard.threads

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.base.BaseViewModel
import com.stathis.domain.model.forum.ForumThread
import com.stathis.domain.usecases.forum.FetchForumThreadsUseCase
import com.stathis.seriesmania.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForumViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val threadsCombiner: FetchForumThreadsUseCase
) : BaseViewModel(app) {

    val threads: MutableLiveData<List<ForumThread>>
        get() = _threads

    private val _threads = MutableLiveData<List<ForumThread>>()

    fun fetchThreads() {
        viewModelScope.launch(dispatcher) {
            val result = threadsCombiner.invoke()
            _threads.postValue(result)
        }
    }
}