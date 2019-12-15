package com.grayimediary.testmania.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.grayimediary.testmania.R
import com.grayimediary.testmania.RESULT_EXTRA
import com.grayimediary.testmania.ui.activity.ResultActivity
import com.grayimediary.testmania.TEST_EXTRA
import com.grayimediary.testmania.viewModel.TestViewModel
import kotlinx.android.synthetic.main.fragment_test_question.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TestFragment : Fragment() {

    val viewModel by sharedViewModel<TestViewModel>()
    var chosenIndex = -1
    var resultText = ""
    var questionNumber = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test_question, null)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getQuestion()

        setQuestionNumber()

        viewModel.testLive.observe(this, Observer {
            question_title.text = it.questionTitle
            option_1.text = it.options[0]
            option_2.text = it.options[1]
            option_3.text = it.options[2]
            option_4.text = it.options[3]

            resultText = it.questionTitle
        })

        viewModel.toResultScreenLive.observe(this, Observer {
            if (it) {
                val intent = Intent(activity, ResultActivity::class.java).apply {
                    putExtra(RESULT_EXTRA, viewModel.results)
                }
                startActivity(intent)
                activity?.finish()
            }
        })

        button_next.setOnClickListener {
            viewModel.nextQuestion(chosenIndex, resultText)
            questionNumber++
            chosenIndex = -1
            resultText = ""
            clearOptions()
            setQuestionNumber()
        }

        setOptionsClickListeners()
    }

    private fun setQuestionNumber() {
        question_count.text = "$questionNumber из ${viewModel.questions.size}"
    }

    private fun setOptionsClickListeners() {
        option_1.setOnClickListener {
            option_1.background = ContextCompat.getDrawable(context!!, R.drawable.background_rectangle_rounded_corners)
            option_1.setTextColor(ContextCompat.getColor(context!!, R.color.colorTestRed))

            option_2.background = ContextCompat.getDrawable(context!!,R.drawable.background_option_stroke)
            option_2.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))

            option_2.background = ContextCompat.getDrawable(context!!,R.drawable.background_option_stroke)
            option_2.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))

            option_4.background = ContextCompat.getDrawable(context!!,R.drawable.background_option_stroke)
            option_4.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))

            chosenIndex = 1
        }

        option_2.setOnClickListener {
            option_2.background = ContextCompat.getDrawable(context!!, R.drawable.background_rectangle_rounded_corners)
            option_2.setTextColor(ContextCompat.getColor(context!!, R.color.colorTestRed))

            option_1.background = ContextCompat.getDrawable(context!!,R.drawable.background_option_stroke)
            option_1.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))

            option_3.background = ContextCompat.getDrawable(context!!,R.drawable.background_option_stroke)
            option_3.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))

            option_4.background = ContextCompat.getDrawable(context!!,R.drawable.background_option_stroke)
            option_4.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))

            chosenIndex = 2
        }

        option_3.setOnClickListener {
            option_3.background = ContextCompat.getDrawable(context!!, R.drawable.background_rectangle_rounded_corners)
            option_3.setTextColor(ContextCompat.getColor(context!!, R.color.colorTestRed))

            option_2.background = ContextCompat.getDrawable(context!!,R.drawable.background_option_stroke)
            option_2.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))

            option_1.background = ContextCompat.getDrawable(context!!,R.drawable.background_option_stroke)
            option_1.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))

            option_4.background = ContextCompat.getDrawable(context!!,R.drawable.background_option_stroke)
            option_4.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))

            chosenIndex = 3
        }

        option_4.setOnClickListener {
            option_4.background = ContextCompat.getDrawable(context!!, R.drawable.background_rectangle_rounded_corners)
            option_4.setTextColor(ContextCompat.getColor(context!!, R.color.colorTestRed))

            option_2.background = ContextCompat.getDrawable(context!!,R.drawable.background_option_stroke)
            option_2.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))

            option_3.background = ContextCompat.getDrawable(context!!,R.drawable.background_option_stroke)
            option_3.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))

            option_1.background = ContextCompat.getDrawable(context!!,R.drawable.background_option_stroke)
            option_1.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))

            chosenIndex = 4
        }
    }

    private fun clearOptions() {
        option_1.background = ContextCompat.getDrawable(context!!,R.drawable.background_option_stroke)
        option_1.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))

        option_2.background = ContextCompat.getDrawable(context!!,R.drawable.background_option_stroke)
        option_2.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))

        option_2.background = ContextCompat.getDrawable(context!!,R.drawable.background_option_stroke)
        option_2.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))

        option_4.background = ContextCompat.getDrawable(context!!,R.drawable.background_option_stroke)
        option_4.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
    }
}