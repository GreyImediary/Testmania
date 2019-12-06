package com.grayimediary.testmania.repository

import com.grayimediary.testmania.net.PostTest
import com.grayimediary.testmania.net.TestApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class TestRepository(private val testApi: TestApi) {

    suspend fun createTest(postTest: PostTest) = withContext(Dispatchers.IO) {
        try {
            testApi.createTest(postTest).await()
        } catch (t: Throwable) {
            throw t
        }
    }

    suspend fun getTests() = withContext(Dispatchers.IO) {
        try {
            testApi.getTests().await()
        } catch (t: Throwable) {
            throw t
        }
    }

    suspend fun getTestById(id: Int) = withContext(Dispatchers.IO) {
        try {
            testApi.getTestById(id).await()
        } catch (t: Throwable) {
            throw t
        }
    }

    suspend fun deleteTest(id: Int) = withContext(Dispatchers.IO) {
        try {
            val response = testApi.deleteTest(id).await()

            val affected = (response["affected"] as Double).toInt()
            affected == 1
        } catch (t: Throwable) {
            throw t
        }
    }
}