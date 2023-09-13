package com.majidebrahimi.local

import com.majidebrahimi.common_test.datasets.UserDataset.DATE_REFRESH
import com.majidebrahimi.common_test.datasets.UserDataset.FAKE_USERS
import com.majidebrahimi.local.base.BaseTest
import com.majidebrahimi.model.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class UserDaoTest: BaseTest() {

    override fun setUp(){
        super.setUp()
        fillDatabase()
    }

    @Test
    fun getTopUsersFromDb() = runBlocking {
        val users = database.userDao().getTopUsers()
        assertEquals(3, users.value?.size)
        compareTwoUsers(FAKE_USERS.first(), users.value?.first()!!)
    }

    @Test
    fun getUser() = runBlocking {
        val user = database.userDao().getUser(FAKE_USERS.first().login)
        compareTwoUsers(FAKE_USERS.first(), user.value!!)
    }

    @Test
    fun saveUser_DateMustChange() = runBlocking {
            database.userDao().save(FAKE_USERS.first())
            val user = database.userDao().getUser(FAKE_USERS.first().login)
            assertNotEquals(DATE_REFRESH, user.value?.lastRefreshed)
    }

    @Test
    fun saveUsers_DateMustChange() = runBlocking {
        database.userDao().save(FAKE_USERS)
        val users = database.userDao().getTopUsers()
        assertNotEquals(DATE_REFRESH, users.value?.first()?.lastRefreshed)
    }

    // ---

    private fun compareTwoUsers(user: User, userToTest: User){
        assertEquals(user.id, userToTest.id)
        assertEquals(user.name, userToTest.name)
        assertEquals(user.login, userToTest.login)
        assertEquals(user.avatarUrl, userToTest.avatarUrl)
        assertEquals(user.company, userToTest.company)
        assertEquals(user.blog, userToTest.blog)
        assertEquals(user.lastRefreshed, userToTest.lastRefreshed)
    }

    private fun fillDatabase() {
        runBlocking {
            database.userDao().save(FAKE_USERS)
        }
    }
}