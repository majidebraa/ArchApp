package com.majidebrahimi.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.majidebrahimi.common.base.BaseViewModel

/**
 * A simple [BaseViewModel] that provide the data and handle logic to communicate with the model
 * for [DetailImageFragment].
 */
class DetailImageViewModel(argsImageUrl: String): BaseViewModel() {

    // PRIVATE DATA
    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> get() = _imageUrl

    // PUBLIC ACTIONS ---
    init {
        _imageUrl.value = argsImageUrl
    }
}