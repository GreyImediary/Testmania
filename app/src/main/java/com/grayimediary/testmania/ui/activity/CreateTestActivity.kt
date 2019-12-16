package com.grayimediary.testmania.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.grayimediary.testmania.R
import com.grayimediary.testmania.USER_ID
import com.grayimediary.testmania.model.Question
import com.grayimediary.testmania.net.PostTest
import com.grayimediary.testmania.ui.adapter.QuestionAdapter
import com.grayimediary.testmania.viewModel.CreateTestViewModel
import kotlinx.android.synthetic.main.activity_create_test.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateTestActivity : AppCompatActivity() {
    private val viewModel by viewModel<CreateTestViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_test)

        val userId = intent.getIntExtra(USER_ID, 0)

        val adapter = QuestionAdapter(arrayListOf(Question("", arrayListOf<String>(), 1)))
        rv_question.adapter = adapter
        rv_question.layoutManager = LinearLayoutManager(this)

        button_add.setOnClickListener {
            adapter.addQuestion(Question("", arrayListOf<String>(), 1))
        }

        button_done.setOnClickListener {
            val title = cr_test_title_edit.text.toString()
            val options = adapter.list
            val desc = cr_test_desc_edit.text.toString()

            viewModel.createTest(PostTest(title, desc, options, userId))
        }

        viewModel.isCreated.observe(this, Observer {
            if (it) {
                finish()
            }
        })
    }
}