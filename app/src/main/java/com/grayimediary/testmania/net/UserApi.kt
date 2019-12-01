package com.grayimediary.testmania.net

import com.google.gson.annotations.SerializedName
import com.grayimediary.testmania.AnyMap
import com.grayimediary.testmania.model.User
import retrofit2.Call
import retrofit2.http.*

interface UserApi {
    @GET("/user/auth")
    fun auth(
        @Query("login") login: String,
        @Query("password") password: String): Call<AnyMap>

    @GET("/user/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>

    @POST("/user")
    fun createUser(@Body postUser: PostUser): Call<User>

    @PUT("/user/{id}")
    fun updateuser(@Path("id") id: Int): Call<User>

    @DELETE("/user/{id}")
    fun deleteUser(@Path("id") id: Int): Call<AnyMap>
}

data class PostUser(
    @SerializedName("password")
    val password: String,

    @SerializedName("login")
    val login: String,

    @SerializedName("email")
    val email: String
)