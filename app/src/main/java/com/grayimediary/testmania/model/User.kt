package com.grayimediary.testmania.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("id")
    val id: Int,

    @SerializedName("login")
    val login: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String?,

    @SerializedName("finishedTests")
    val finishedTests: ArrayList<FinishedTest>,

    @SerializedName("createdTests")
    val createdTests: ArrayList<CreatedTest>
) : Serializable

data class CreatedTest(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("createdAt")
    val createdAt: String
) : Serializable

data class FinishedTest(
    @SerializedName("id")
    val id: Int,

    @SerializedName("result")
    val result: ArrayList<Boolean>
) : Serializable