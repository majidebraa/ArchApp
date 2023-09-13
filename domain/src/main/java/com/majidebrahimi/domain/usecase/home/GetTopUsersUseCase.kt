package com.majidebrahimi.domain.usecase.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.majidebrahimi.domain.repositories.UserRepository
import com.majidebrahimi.domain.utils.Resource
import com.majidebrahimi.model.User

/**
 * Use case that gets a [Resource][List][User] from [UserRepository]
 * and makes some specific logic actions on it.
 *
 * In this Use Case, I'm just doing nothing... ¯\_(ツ)_/¯
 */
class GetTopUsersUseCase(private val repository: UserRepository) {

    suspend operator fun invoke(): LiveData<Resource<List<User>>> {
        return repository.getTopUsersWithCache().map {
            it // Place here your specific logic actions
        }
    }
}