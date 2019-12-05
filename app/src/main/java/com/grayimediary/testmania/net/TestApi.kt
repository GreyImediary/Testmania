package com.grayimediary.testmania.net

import com.google.gson.annotations.SerializedName
import com.grayimediary.testmania.AnyMap
import com.grayimediary.testmania.model.Question
import com.grayimediary.testmania.model.Test
import retrofit2.Call
import retrofit2.http.*

interface TestApi {
    @POST("/test")
    fun createTest(@Body postTest: PostTest): Call<Test>

    @GET("/test")
    fun getTests(): Call<List<Test>>

    @GET("/test/{id}")
    fun getTestById(@Path("id") id: Int): Call<Test>

    @DELETE("/test/{id}")
    fun deleteTest(@Path("id") id: Int): Call<AnyMap>
}

data class PostTest(
    val title: String,
    val description: String,
    val questions: ArrayList<Question>,
    @SerializedName("createdBy")
    val authorLogin: String
)