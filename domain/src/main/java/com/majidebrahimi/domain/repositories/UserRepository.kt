package com.majidebrahimi.domain.repositories

import androidx.lifecycle.LiveData
import com.majidebrahimi.domain.utils.Resource
import com.majidebrahimi.model.User


interface UserRepository {
    suspend fun getTopUsers(): LiveData<Resource<List<User>>>
    suspend fun getTopUsersWithCache(): LiveData<Resource<List<User>>>
    suspend fun getUserDetailWithCache(login: String): LiveData<Resource<User>>
}