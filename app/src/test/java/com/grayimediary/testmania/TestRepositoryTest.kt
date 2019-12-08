package com.grayimediary.testmania

import com.google.common.truth.Truth.assertThat
import com.grayimediary.testmania.di.apiModule
import com.grayimediary.testmania.di.repositoryModule
import com.grayimediary.testmania.model.Question
import com.grayimediary.testmania.net.PostTest
import com.grayimediary.testmania.repository.TestRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class TestRepositoryTest : KoinTest {
    private val testRepository: TestRepository by inject()
    private var testId = 0

    @Before
    fun setUp() {
        startKoin { modules(listOf(apiModule, repositoryModule)) }

        runBlocking {
           val test =  testRepository.createTest(
                PostTest(
                    "testTest",
                    "just a test",
                    arrayListOf(Question("question", arrayListOf("1", "2", "3", "4"), 2)),
                    authorId = 1
                )
            )

            testId = test.id
        }
    }

    @Test
    fun when_TestIdNot0_Expect_TestCreated() {
        assertThat(testId).isNotEqualTo(0)
    }

    @Test
    fun when_GetTests_Expect_ListNotNull() {
        val tests = runBlocking { testRepository.getTests() }

        assertThat(tests).isNotEmpty()
    }

    @Test
    fun when_getTestById_Expect_testNotNull() {
        val test = runBlocking { testRepository.getTestById(1) }

        assertThat(test).isNotNull()
    }

    @Test
    fun when_DeleteTest_Expect_True() {
        val isDeleted = runBlocking { testRepository.deleteTest(testId) }

        assertThat(isDeleted).isTrue()
    }

    @After
    fun shutDown() {
        stopKoin()
    }
}