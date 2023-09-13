package com.majidebrahimi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.majidebrahimi.domain.repositories.UserRepository
import com.majidebrahimi.model.User
import com.majidebrahimi.remote.UserDatasource
import com.majidebrahimi.domain.utils.Resource
import com.majidebrahimi.local.dao.UserDao
import com.majidebrahimi.remote.error.ErrorHandler

class UserRepositoryImpl(
    private val dataSource: UserDatasource,
    private val userDao: UserDao,
    private val handler: ErrorHandler
) :
    UserRepository {

    /**
     * Suspended function that will get a list of top [User]
     * via network (API).
     */
    override suspend fun getTopUsers(): LiveData<Resource<List<User>>> =

        liveData {
            emit(Resource.loading<List<User>>(null))
            try {
                val res = dataSource.fetchTopUsersAsync()
                emit(Resource.success(res.items))
            } catch (exception: Exception) {
                //exception.localizedMessage.
                val info = handler.handle(exception, true)

                emit(Resource.error<List<User>>(info.message, null))
            }

        }

    /**
     * Suspended function that will get a list of top [User]
     * whether in cache (SQLite) or via network (API).
     */

    override suspend fun getTopUsersWithCache(): LiveData<Resource<List<User>>> =
        liveData {
            val disposable = emitSource(
                userDao.getTopUsers().map {
                    Resource.loading(it)
                }
            )
            try {
                val user = dataSource.fetchTopUsersAsync()
                // Stop the previous emission to avoid dispatching the updated user
                // as `loading`.
                disposable.dispose()
                // Update the database.
                userDao.save(user.items)
                // Re-establish the emission with success type.
                emitSource(
                    userDao.getTopUsers().map {
                        Resource.success(it)
                    }
                )
            } catch (exception: Exception) {
                // Any call to `emit` disposes the previous one automatically so we don't
                // need to dispose it here as we didn't get an updated value.
                //exception.localizedMessage.
                val info = handler.handle(exception, true)
                emitSource(
                    userDao.getTopUsers().map {
                        Resource.error(info.message, it)
                    }
                )
            }
        }


    /**
     * Suspended function that will get details of a [User]
     * whether in cache (SQLite) or via network (API).
     */

    override suspend fun getUserDetailWithCache(login: String): LiveData<Resource<User>> =
        liveData {
            val disposable = emitSource(
                userDao.getUser(login).map {
                    Resource.loading(it)
                }
            )

            try {
                val user = dataSource.fetchUserDetailsAsync(login)
                // Stop the previous emission to avoid dispatching the updated user
                // as `loading`.
                disposable.dispose()
                // Update the database.
                userDao.save(user)
                // Re-establish the emission with success type.
                emitSource(
                    userDao.getUser(login).map {
                        Resource.success(it)
                    }
                )
            } catch (exception: Exception) {
                // Any call to `emit` disposes the previous one automatically so we don't
                // need to dispose it here as we didn't get an updated value.
                val info = handler.handle(exception, true)
                emitSource(
                    userDao.getUser(login).map {
                        Resource.error(info.message!!, it)
                    }
                )
            }
        }
}