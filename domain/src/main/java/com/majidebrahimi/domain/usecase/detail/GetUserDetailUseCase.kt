package com.majidebrahimi.domain.usecase.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.majidebrahimi.domain.repositories.UserRepository
import com.majidebrahimi.domain.utils.Resource
import com.majidebrahimi.model.User


/**
 * Use case that gets a [Resource] [User] from [UserRepository]
 * and makes some specific logic actions on it.
 *
 * In this Use Case, I'm just doing nothing... ¯\_(ツ)_/¯
 */
class GetUserDetailUseCase(private val repository: UserRepository) {

    suspend operator fun invoke(login: String): LiveData<Resource<User>> {
        return repository.getUserDetailWithCache(login).map {
            it // Place here your specific logic actions (if any)
        }
    }
}