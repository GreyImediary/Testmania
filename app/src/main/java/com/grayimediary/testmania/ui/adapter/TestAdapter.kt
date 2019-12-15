package com.grayimediary.testmania.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grayimediary.testmania.R
import com.grayimediary.testmania.inflate
import com.grayimediary.testmania.model.Test
import com.grayimediary.testmania.visible
import kotlinx.android.synthetic.main.item_test.view.*

class TestAdapter(
    private val author: String,
    private val onTestClick: (test: Test) -> Unit,
    private val onDeleteClick: (testId: Int) -> Unit
) : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

    var tests: List<Test> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TestViewHolder(parent.inflate(R.layout.item_test, false))

    override fun getItemCount() = tests.size

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) =
        holder.bind(tests[position])

    inner class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(test: Test) {
            itemView.test_title.text = test.title
            itemView.test_author.text = test.authorLogin
            itemView.test_question_count.text =
                itemView.context.getString(R.string.question_count, test.questions.count())

            if (test.authorLogin == author) {
                itemView.button_delete.visible()
            }

            itemView.setOnClickListener { onTestClick(test) }
            itemView.button_delete.setOnClickListener { onDeleteClick(test.id) }
        }
    }
}