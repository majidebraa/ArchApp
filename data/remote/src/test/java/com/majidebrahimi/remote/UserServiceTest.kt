package com.majidebrahimi.remote

import com.majidebrahimi.remote.base.BaseTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import java.net.HttpURLConnection

class UserServiceTest: BaseTest() {

    @Test
    fun `search top users by name`() {
        mockHttpResponse(mockServer, "search_users.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val users = userService.fetchTopUsersAsync()
            assertEquals(1, users.items.size)
            assertEquals("6847959", users.items.first().id)
            assertEquals("mokhtar", users.items.first().login)
            assertEquals("https://avatars0.githubusercontent.com/u/6847959?v=4", users.items.first().avatarUrl)
        }
    }

    @Test(expected = HttpException::class)
    fun `search top users by name and fail`() {
        mockHttpResponse(mockServer,"search_users.json", HttpURLConnection.HTTP_FORBIDDEN)
        runBlocking {
            userService.fetchTopUsersAsync()
        }
    }

    // ---

    @Test
    fun `fetch user's detail`() {
        mockHttpResponse(mockServer,"user_detail.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val user = userService.fetchUserDetailsAsync("mokhtar")
            assertEquals("6847959", user.id)
            assertEquals("mokhtar", user.login)
            assertEquals("https://avatars0.githubusercontent.com/u/6847959?v=4", user.avatarUrl)
            assertEquals("mokhtar ahmadi", user.name)
            assertEquals("https://medium.com/@majidebrahimi", user.blog)
            assertEquals("CookMinute", user.company)
        }
    }

    @Test(expected = HttpException::class)
    fun `fetch user's detail and fail`() {
        mockHttpResponse(mockServer,"user_detail.json", HttpURLConnection.HTTP_FORBIDDEN)
        runBlocking {
            userService.fetchUserDetailsAsync("mokhtar")
        }
    }
}