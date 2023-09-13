package com.majidebrahimi.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.majidebrahimi.common.base.BaseViewModel
import com.majidebrahimi.common.utils.Event
import com.majidebrahimi.domain.usecase.detail.GetUserDetailUseCase
import com.majidebrahimi.domain.utils.AppDispatchers
import com.majidebrahimi.domain.utils.Resource
import com.majidebrahimi.model.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.majidebrahimi.common.R


/**
 * A simple [BaseViewModel] that provide the data and handle logic to communicate with the model
 * for [DetailFragment].
 */
class DetailViewModel(private val getUserDetailUseCase: GetUserDetailUseCase,
                      private val dispatchers: AppDispatchers,
                      private val argsLogin: String
): BaseViewModel() {

    // PRIVATE DATA
    private var userSource: LiveData<Resource<User>> = MutableLiveData()

    private val _user = MediatorLiveData<User?>()
    val user: LiveData<User?> get() = _user
    private val _isLoading = MutableLiveData<Resource.Status>()
    val isLoading: LiveData<Resource.Status> get() = _isLoading

    // PUBLIC ACTIONS ---
     init {
        getUserDetail()
    }

    fun reloadDataWhenUserRefreshes() = getUserDetail()

    fun userClicksOnAvatarImage(user: User)
            = user.avatarUrl?.let {
        DetailFragmentDirections.actionDetailFragmentToImageDetailFragment(
            it
        )
    }?.let {
        navigate(
            it
            )
    }

    // ---

    private fun getUserDetail() = viewModelScope.launch(dispatchers.main) {
        _user.removeSource(userSource) // We make sure there is only one source of livedata (allowing us properly refresh)
        withContext(dispatchers.io) { userSource = getUserDetailUseCase(login = argsLogin) }
        _user.addSource(userSource) {
            _user.value = it.data
            _isLoading.value = it.status
            if (it.status == Resource.Status.ERROR) _snackbarError.value = Event(R.string.an_error_happened)
        }
    }
}