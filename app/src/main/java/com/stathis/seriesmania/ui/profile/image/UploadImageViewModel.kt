package com.stathis.seriesmania.ui.profile.image

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.core.base.BaseViewModel
import com.stathis.domain.usecases.profile.GetProfileInfoUseCase
import com.stathis.domain.usecases.profile.UploadProfileImageUseCase
import com.stathis.seriesmania.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadImageViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val uploadImageUseCase: UploadProfileImageUseCase,
    private val getProfileInfoUseCase: GetProfileInfoUseCase
) : BaseViewModel(app) {

    val bitmapSaved: LiveData<Boolean>
        get() = _bitmapSaved

    private val _bitmapSaved = MutableLiveData<Boolean>()

    val bitmap: LiveData<Bitmap>
        get() = _bitmap

    private val _bitmap = MutableLiveData<Bitmap>()

    val userImage: LiveData<String>
        get() = _userImage

    private val _userImage = MutableLiveData<String>()

    val ctaState: LiveData<Boolean>
        get() = _ctaState

    private val _ctaState = MutableLiveData<Boolean>()

    private var _tempBitmap: Bitmap? = null

    fun saveUserPhoto(bitmap: Bitmap) {
        _tempBitmap = bitmap
        _bitmap.postValue(bitmap)
        _ctaState.postValue(true)
    }

    fun getUserImage() {
        viewModelScope.launch(dispatcher) {
            val result = getProfileInfoUseCase.invoke()
            _userImage.postValue(result.userImg)
        }
    }

    fun saveUserImage() {
        viewModelScope.launch(dispatcher) {
            _tempBitmap?.let { bitmap ->
                val result = uploadImageUseCase.invoke(bitmap)
                _bitmapSaved.postValue(result)
            }
        }
    }
}