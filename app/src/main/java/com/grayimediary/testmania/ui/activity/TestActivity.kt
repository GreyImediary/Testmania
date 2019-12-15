package com.grayimediary.testmania.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grayimediary.testmania.R
import com.grayimediary.testmania.TEST_EXTRA
import com.grayimediary.testmania.USER_ID
import com.grayimediary.testmania.model.Test
import com.grayimediary.testmania.viewModel.TestViewModel
import kotlinx.android.synthetic.main.activity_test_results.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestActivity : AppCompatActivity() {

    val viewModel by viewModel<TestViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val test = intent.getSerializableExtra(TEST_EXTRA) as Test

        viewModel.questions = test.questions
        viewModel.test = test
        viewModel.userId = intent.getIntExtra(USER_ID, -1)
    }
}
