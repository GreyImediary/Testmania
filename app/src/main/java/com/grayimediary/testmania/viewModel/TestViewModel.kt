package com.grayimediary.testmania.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grayimediary.testmania.model.FinishedTest
import com.grayimediary.testmania.model.Question
import com.grayimediary.testmania.model.Test
import com.grayimediary.testmania.repository.UserRepository
import kotlinx.coroutines.launch

class TestViewModel(private val repository: UserRepository) : ViewModel() {
    private var questionIndex = 0
    val testLive = MutableLiveData<Question>()
    val toResultScreenLive = MutableLiveData<Boolean>()

    val results = ArrayList<Pair<String, Boolean>>()
    var userId = -1

    var questions = emptyList<Question>()
    var test: Test? = null

    fun nextQuestion(optionIndex: Int, resultText: String) {
        val rightOption = questions[questionIndex].answerNumber

        results.add(Pair(resultText, optionIndex == rightOption))

        if (questionIndex == questions.size - 1) {
            viewModelScope.launch {
                try {
                    val resultBooleans =
                        results.map { it.second }.toMutableList() as ArrayList<Boolean>

                    repository.updateTests(userId, FinishedTest(test!!.id, resultBooleans))
                } catch (t: Throwable) {

                }
            }

            toResultScreenLive.value = true

        } else {
            questionIndex++
            getQuestion()
        }
    }

    fun getQuestion() {
        testLive.value = questions[questionIndex]
    }
}