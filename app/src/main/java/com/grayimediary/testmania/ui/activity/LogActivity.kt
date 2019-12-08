package com.grayimediary.testmania.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.grayimediary.testmania.LOGIN_KEY
import com.grayimediary.testmania.R
import com.grayimediary.testmania.prefName
import com.grayimediary.testmania.viewModel.LogInViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogActivity : AppCompatActivity() {

    private val viewModel by viewModel<LogInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        val login = getSharedPreferences(prefName, Context.MODE_PRIVATE).getString(LOGIN_KEY, "") ?: ""

        if (login.isNotBlank()) {
            viewModel.getUserByLogin(login)
        }
    }
}
