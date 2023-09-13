package com.majidebrahimi.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import io.mockk.*
import com.majidebrahimi.common_test.rules.CoroutinesMainDispatcherRule
import com.majidebrahimi.domain.repositories.UserRepository
import com.majidebrahimi.domain.utils.Resource
import com.majidebrahimi.model.ApiResult
import com.majidebrahimi.model.User
import com.majidebrahimi.remote.UserDatasource
import com.majidebrahimi.remote.error.ErrorHandler
import com.majidebrahimi.remote.error.ErrorHandlerImpl
import com.majidebrahimi.repository.utils.FakeData
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesMainDispatcherRule = CoroutinesMainDispatcherRule()

    // FOR DATA
    private lateinit var observer: Observer<Resource<List<User>>>
    private lateinit var observerUser: Observer<Resource<User>>
    private lateinit var userRepository: UserRepository
    private val userService = mockk<UserDatasource>()
    private val userDao = mockk<UserDao>(relaxed = true)
    private val handler = mockk<ErrorHandlerImpl>()

    @Before
    fun setUp() {
        observer = mockk(relaxed = true)
        observerUser = mockk(relaxed = true)

        userRepository =
            UserRepositoryImpl(userService, userDao, handler)
    }

    @Test
    fun `Get top users from server when no internet is available`() {
        val exception = Exception("Internet")
        val info = handler.handle(exception, true)
        coEvery { userService.fetchTopUsersAsync() } throws exception
        coEvery { userDao.getTopUsers() } returns liveData { listOf<User>() }

        runBlocking {
            userRepository.getTopUsersWithCache().observeForever(observer)
        }

        verifyOrder {
            observer.onChanged(Resource.loading(null)) // Init loading with no value
            observer.onChanged(Resource.loading(listOf())) // Then trying to load from db (fast temp loading) before load from remote source
            observer.onChanged(Resource.error(info.message!!, listOf())) // Retrofit 403 error
        }
        confirmVerified(observer)
    }


    @Test
    fun `Get top users from network`() {
        val fakeUsers = FakeData.createFakeUsers(5)
        coEvery { userService.fetchTopUsersAsync() } returns ApiResult(fakeUsers.size, fakeUsers)
        coEvery { userDao.getTopUsers() } returns liveData { listOf<User>() } andThen { liveData { fakeUsers } }

        runBlocking {
            userRepository.getTopUsersWithCache().observeForever(observer)
        }

        verifyOrder {
            observer.onChanged(Resource.loading(null)) // Loading from remote source
            observer.onChanged(Resource.loading(listOf())) // Then trying to load from db (fast temp loading) before load from remote source
            observer.onChanged(Resource.success(fakeUsers)) // Success
        }

        coVerify(exactly = 1) {
            userDao.save(fakeUsers)
        }

        confirmVerified(observer)
    }

    @Test
    fun `Get top users from db`() {
        val fakeUsers = FakeData.createFakeUsers(5)
        coEvery { userService.fetchTopUsersAsync() } returns ApiResult(fakeUsers.size, fakeUsers)
        coEvery { userDao.getTopUsers() } returns liveData { fakeUsers }

        runBlocking {
            userRepository.getTopUsersWithCache().observeForever(observer)
        }

        verifyOrder {
            observer.onChanged(Resource.loading(null)) // Loading from remote source
            observer.onChanged(Resource.success(fakeUsers)) // Success
        }

        confirmVerified(observer)
    }

    @Test
    fun `Get user's detail from network`() {
        val fakeUser = FakeData.createFakeUser("fake")
        coEvery { userService.fetchUserDetailsAsync("fake_login") } returns fakeUser
        coEvery { userDao.getUser("fake_login") } returns liveData { fakeUser }

        runBlocking {
            userRepository.getUserDetailWithCache(login = "fake_login").observeForever(observerUser)
        }

        verify {
            observerUser.onChanged(Resource.loading(null)) // Loading from remote source
            observerUser.onChanged(Resource.loading(fakeUser)) // Then trying to load from db (fast temp loading) before load from remote source
            observerUser.onChanged(Resource.success(fakeUser)) // Success
        }

        coVerify(exactly = 1) {
            userDao.save(fakeUser)
        }

        confirmVerified(observerUser)
    }

    @Test
    fun `Get user's detail from db`() {
        val fakeUser = FakeData.createFakeUser("fake")

        coEvery { userService.fetchUserDetailsAsync("fake_login") } returns fakeUser
        coEvery { userDao.getUser("fake_login") } returns liveData { fakeUser.apply { lastRefreshed = Date() } }

        runBlocking {
            userRepository.getUserDetailWithCache(login = "fake_login").observeForever(observerUser)
        }

        verify {
            observerUser.onChanged(Resource.loading(null)) // Loading from remote source
            observerUser.onChanged(Resource.success(fakeUser)) // Success
        }

        confirmVerified(observerUser)
    }

}