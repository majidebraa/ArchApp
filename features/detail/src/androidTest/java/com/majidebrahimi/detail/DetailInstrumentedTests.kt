package com.majidebrahimi.detail

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import com.majidebrahimi.common_test.datasets.UserDataset.FAKE_USERS
import com.majidebrahimi.detail.di.featureDetailModule
import com.majidebrahimi.domain.repositories.UserRepository
import com.majidebrahimi.domain.utils.AppDispatchers
import com.majidebrahimi.domain.utils.Resource
import com.majidebrahimi.model.User
import kotlinx.coroutines.Dispatchers
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import java.lang.Exception


@RunWith(AndroidJUnit4::class)
@LargeTest
class DetailInstrumentedTests : KoinTest {

    private val userRepository = mockk<UserRepository>()

    @Before
    fun setUp() {
        loadKoinModules(listOf(featureDetailModule, module {
            factory { AppDispatchers(Dispatchers.Main, Dispatchers.Main) }
            factory { userRepository }
        }))
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testIfUserIsCorrectlyDisplayed() {
        val user = FAKE_USERS.first()
        coEvery {
            userRepository.getUserDetailWithCache(
                "fake"
            )
        } returns MutableLiveData<Resource<User>>().apply { postValue(Resource.success(user)) }
        launchFragment()

        onView(ViewMatchers.withId(R.id.fragment_detail_blog)).check(
            matches(
                withText(
                    containsString(
                        user.blog
                    )
                )
            )
        )
        onView(ViewMatchers.withId(R.id.fragment_detail_company)).check(
            matches(
                withText(
                    containsString(user.company)
                )
            )
        )
        onView(ViewMatchers.withId(R.id.fragment_detail_name)).check(
            matches(
                withText(
                    containsString(
                        user.name
                    )
                )
            )
        )
    }

    @Test
    fun testRefreshWhenError() {
        coEvery {
            userRepository.getUserDetailWithCache(
                "fake"
            )
        } returns MutableLiveData<Resource<User>>().apply {
            postValue(
                Resource.error(
                    "403",
                    FAKE_USERS.first()
                )
            )
        }
        launchFragment()

        onView(ViewMatchers.withId(R.id.fragment_detail_root_view)).perform(ViewActions.swipeDown())

        onView(ViewMatchers.withId(com.google.android.material.R.id.snackbar_text)).check(
            matches(
                withText(R.string.an_error_happened)
            )
        )
    }

    @Test
    fun testNavigationToDetailImageScreen() {
        coEvery {
            userRepository.getUserDetailWithCache(
                "fake"
            )
        } returns MutableLiveData<Resource<User>>().apply { postValue(Resource.success(FAKE_USERS.first())) }
        val mockNavController = launchFragment()

        Espresso.onView(ViewMatchers.withId(R.id.fragment_detail_avatar)).perform(click())

        verify {
            mockNavController.navigate(
                DetailFragmentDirections.actionDetailFragmentToImageDetailFragment(
                    FAKE_USERS.first().avatarUrl
                ), any<FragmentNavigator.Extras>()
            )
        }
    }

    // ---

    private fun launchFragment(): NavController {
        val mockNavController = mockk<NavController>(relaxed = true)
        val detailScenario =
            launchFragmentInContainer<DetailFragment>(fragmentArgs = Bundle().apply {
                putString(
                    "login",
                    "fake"
                )
            }, themeResId = R.style.AppTheme)
        detailScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        return mockNavController
    }
}