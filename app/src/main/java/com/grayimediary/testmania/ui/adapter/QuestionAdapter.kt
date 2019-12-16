package com.grayimediary.testmania.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.grayimediary.testmania.R
import com.grayimediary.testmania.inflate
import com.grayimediary.testmania.model.Question
import kotlinx.android.synthetic.main.item_create_test_question.view.*

class QuestionAdapter(val list: ArrayList<Question>) :
    RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        QuestionViewHolder(parent.inflate(R.layout.item_create_test_question))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) =
        holder.bind(list[position])

    fun addQuestion(question: Question) {
        list.add(question)
        notifyDataSetChanged()
    }

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(question: Question) {
            itemView.question_title_edit.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    question.questionTitle = s.toString()
                }
            })

            itemView.question_option_1_edit.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (question.options.size > 1) {
                        question.options.removeAt(0)
                        question.options.add(0, s.toString())
                    } else {
                        question.options.add(s.toString())
                    }
                }
            })

            itemView.question_option_2_edit.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (question.options.size > 2) {
                        question.options.removeAt(1)
                        if (question.options.size > 1) {
                            question.options.add(1, s.toString())
                        } else {
                            question.options.add(s.toString())
                        }
                    } else {
                        question.options.add(s.toString())
                    }
                }
            })

            itemView.question_option_3_edit.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (question.options.size > 3) {
                        question.options.removeAt(2)
                        if (question.options.size > 2) {
                            question.options.add(2, s.toString())
                        } else {
                            question.options.add(s.toString())
                        }
                    } else {
                        question.options.add(s.toString())
                    }
                }
            })

            itemView.question_option_4_edit.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (question.options.size > 4) {
                        question.options.removeAt(3)
                        question.options.add(s.toString())
                    } else {
                        question.options.add(s.toString())
                    }
                }
            })

            itemView.question_right_option.setOnClickListener {
                AlertDialog.Builder(itemView.context)
                    .setTitle("Выберите номер правильного варианта")
                    .setItems(arrayOf("1", "2", "3", "4")) {dialog, which ->
                        itemView.question_right_option.text = "${which + 1}"
                        question.answerNumber = which + 1
                    }
                    .create().show()
            }
        }
    }
}