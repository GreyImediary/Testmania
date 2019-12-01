package com.grayimediary.testmania.repository

import com.grayimediary.testmania.model.FinishedTest
import com.grayimediary.testmania.net.PostUser
import com.grayimediary.testmania.net.UpdateTest
import com.grayimediary.testmania.net.UserApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class UserRepository(private val userApi: UserApi) {
    suspend fun createUser(login: String, email: String, password: String) =
        withContext(Dispatchers.IO) {
            try {
                userApi.createUser(PostUser(password, login, email)).await()
            } catch (t: Throwable) {
                throw t
            }
        }

    suspend fun getUserById(id: Int) = withContext(Dispatchers.IO) {
        try {
            userApi.getUserById(id).await()
        } catch (t: Throwable) {
            throw t
        }
    }

    suspend fun authUser(login: String, password: String) = withContext(Dispatchers.IO) {
        try {
            userApi.auth(login, password).await().isPasswordCorrect
        } catch (t: Throwable) {
            throw t
        }
    }

    suspend fun updateTests(id: Int, finishedTest: FinishedTest) = withContext(Dispatchers.IO) {
        try {
            userApi.updateTest(id, UpdateTest(finishedTest)).await()
        } catch (t: Throwable) {
            throw t
        }
    }

    suspend fun deleteUser(id: Int) = withContext(Dispatchers.IO) {
        try {
            val response = userApi.deleteUser(id).await()

            val affected = response["affected"] as? Boolean
            affected ?: false
        } catch (t: Throwable) {
            throw t
        }
    }
}