package com.grayimediary.testmania.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Test(
    val id: Int,
    val title: String,
    val questions: ArrayList<Question>,
    val description: String,
    @SerializedName("createdBy")
    val authorLogin: String,
    val createdAt: String
) : Serializable


data class Question(
    @SerializedName("text")
    var questionTitle: String,

    @SerializedName("options")
    var options: ArrayList<String>,

    @SerializedName("rightOptionId")
    var answerNumber: Int
) : Serializable