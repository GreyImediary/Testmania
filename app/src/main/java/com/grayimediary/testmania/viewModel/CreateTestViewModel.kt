package com.grayimediary.testmania.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grayimediary.testmania.net.PostTest
import com.grayimediary.testmania.repository.TestRepository
import kotlinx.coroutines.launch

class CreateTestViewModel(private val repository: TestRepository) : ViewModel() {
    val isCreated = MutableLiveData<Boolean>()

    fun createTest(test: PostTest) {
        viewModelScope.launch {
            try {
                repository.createTest(test)
                isCreated.value = true
            } catch (t: Throwable) {}
        }
    }
}