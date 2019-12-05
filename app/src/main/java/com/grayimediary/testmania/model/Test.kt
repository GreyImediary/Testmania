package com.grayimediary.testmania.model

import com.google.gson.annotations.SerializedName

data class Test(
    val id: Int,
    val title: String,
    val questions: ArrayList<Question>,
    val description: String,
    @SerializedName("createdBy")
    val authorLogin: String,
    val createdAt: String
)


data class Question(
    @SerializedName("text")
    val questionTitle: String,

    @SerializedName("options")
    val options: ArrayList<String>,

    @SerializedName("rightOptionId")
    val answerNumber: Int
)