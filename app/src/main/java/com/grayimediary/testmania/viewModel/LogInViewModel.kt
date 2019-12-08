package com.grayimediary.testmania.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grayimediary.testmania.R
import com.grayimediary.testmania.model.User
import com.grayimediary.testmania.repository.UserRepository
import kotlinx.coroutines.launch

class LogInViewModel(private val userRepository: UserRepository) : ViewModel() {

    val toastLive = SingleLiveEvent<Int>()
    val userLive = SingleLiveEvent<User>()
    val progressBarLive = MutableLiveData<Boolean>()

    fun createUser(login: String, email: String, password: String) {
        viewModelScope.launch {
            progressBarLive.value = true
            val user = userRepository.createUser(login, email, password)
            userLive.value = user
            progressBarLive.value = false
        }
    }

    fun authUser(login: String, password: String) {
        doJob {
            progressBarLive.value = true
            val isAuth = userRepository.authUser(login, password)

            if (isAuth) {
                userLive.value = userRepository.getUserByLogin(login)
            } else {
                toastLive.value = R.string.wrong_login_pass_error
            }
            progressBarLive.value = false
        }
    }

    fun getUserByLogin(login: String) {
        doJob {
            userLive.value = userRepository.getUserByLogin(login)
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