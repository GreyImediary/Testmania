package com.grayimediary.testmania.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grayimediary.testmania.R
import com.grayimediary.testmania.model.CreatedTest
import com.grayimediary.testmania.model.FinishedTest
import com.grayimediary.testmania.model.Test
import com.grayimediary.testmania.repository.TestRepository
import kotlinx.coroutines.launch

class MainViewModel(private val testRepository: TestRepository) : ViewModel() {
    val progressBarLive = SingleLiveEvent<Boolean>()
    val testsLive = MutableLiveData<List<Test>>()
    val finishedTestsLive = MutableLiveData<List<Test>>()
    val createdTestsLive = MutableLiveData<List<Test>>()
    val toastLive = SingleLiveEvent<Int>()

    fun getTests() {
        doJob {
            progressBarLive.value = true

            testsLive.value = testRepository.getTests()

            progressBarLive.value = false
        }
    }

    fun deleteTest(id: Int) {
        doJob {
            val isDeleted = testRepository.deleteTest(id)

            if (isDeleted) {
                toastLive.value = R.string.test_deleted
            } else {
                toastLive.value = R.string.error
            }
        }
    }

    fun getCreatedTests(createdTests: List<CreatedTest>) {
        doJob {
            progressBarLive.value = true

            val tests = createdTests.map { testRepository.getTestById(it.id) }
            createdTestsLive.value = tests

            progressBarLive.value = false
        }
    }

    fun getFinishedTests(finishedTests: List<FinishedTest>) {
        doJob {
            progressBarLive.value = true

            val tests = finishedTests.map { testRepository.getTestById(it.id) }
            finishedTestsLive.value = tests

            progressBarLive.value = false
        }
    }

    private fun doJob(job: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                job()
            } catch (t: Throwable) {
                toastLive.value = R.string.error
                progressBarLive.value = false
            }
        }
    }
}