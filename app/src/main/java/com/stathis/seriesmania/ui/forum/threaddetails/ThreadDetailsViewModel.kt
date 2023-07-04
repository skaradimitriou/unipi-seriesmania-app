package com.stathis.seriesmania.ui.forum.threaddetails

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.base.BaseViewModel
import com.stathis.domain.model.Result
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.forum.ForumThread
import com.stathis.domain.usecases.forum.AddThreadReplyUseCase
import com.stathis.domain.usecases.forum.FetchForumThreadByIdUseCase
import com.stathis.seriesmania.di.IoDispatcher
import com.stathis.seriesmania.ui.forum.threaddetails.generator.ThreadDetailsGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThreadDetailsViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val addReplyUseCase: AddThreadReplyUseCase,
    private val fetchThreadByIdUseCase: FetchForumThreadByIdUseCase
) : BaseViewModel(app) {

    val details: LiveData<Result<List<UiModel>>>
        get() = _details

    private val _details = MutableLiveData<Result<List<UiModel>>>()

    private var currentForumThread: ForumThread? = null

    fun addReply(reply: String) {
        viewModelScope.launch(dispatcher) {
            addReplyUseCase.invoke(reply, currentForumThread)
            currentForumThread?.let { getThreadDetails(it) }
        }
    }

    fun getThreadDetails(forumThread: ForumThread? = currentForumThread) {
        _details.postValue(Result.Loading())
        viewModelScope.launch(dispatcher) {
            val result = fetchThreadByIdUseCase.invoke(forumThread?.threadId)
            currentForumThread = result
            val details = ThreadDetailsGenerator.generate(result)
            _details.postValue(Result.Success(details))
        }
    }
}