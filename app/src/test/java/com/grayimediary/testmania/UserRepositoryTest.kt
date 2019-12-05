package com.grayimediary.testmania

import com.google.common.truth.Truth.assertThat
import com.grayimediary.testmania.di.apiModule
import com.grayimediary.testmania.di.repositoryModule
import com.grayimediary.testmania.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class UserRepositoryTest : KoinTest {
    private val userRepository: UserRepository by inject()
    var userId: Int = 0

    @Before
    fun setUp() {
        startKoin {
            modules(listOf(apiModule, repositoryModule))
        }

        runBlocking {
            userId = userRepository.createUser(
                "testUser",
                "test@test.com",
                "111"
            ).id
        }
    }

    @Test
    fun when_UserIdNot0_Expect_UserCreated() {
        assertThat(userId).isNotEqualTo(0)
    }

    @Test
    fun when_AuthUser_Expect_True() = runBlocking {
        val isAuth = userRepository.authUser("testUser", "111")

        assertThat(isAuth).isTrue()
    }

    @Test
    fun when_GetUserById_Expect_UserNotNull() = runBlocking {
        val user = userRepository.getUserById(userId)

        assertThat(user).isNotNull()
    }


    @Test
    fun when_DeleteUser_Expect_IsDeletedTrue() = runBlocking {

        val isDeleted = userRepository.deleteUser(userId)

        assertThat(isDeleted).isTrue()
    }

    @After
    fun setDown() {
        runBlocking {
            userRepository.deleteUser(userId)
        }
        stopKoin()
    }
}