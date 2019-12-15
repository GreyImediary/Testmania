package com.grayimediary.testmania.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.grayimediary.testmania.R
import com.grayimediary.testmania.gone
import com.grayimediary.testmania.inflate
import com.grayimediary.testmania.visible
import kotlinx.android.synthetic.main.item_result.view.*

class ResultAdapter(private val list: ArrayList<Pair<String, Boolean>>, private val colorRes: Int) :
    RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ResultViewHolder(parent.inflate(R.layout.item_result, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) =
        holder.bind(list[position], position)

    inner class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(result: Pair<String, Boolean>, position: Int) {
            itemView.item_result_text.text = "${position + 1}. ${result.first}"
            itemView.item_result_text.setTextColor(ContextCompat.getColor(itemView.context, colorRes))
            if (result.second) {
                itemView.item_result_question_passed.visible()
                itemView.item_result_question_failed.gone()
            } else {
                itemView.item_result_question_failed.visible()
                itemView.item_result_question_passed.gone()
            }
        }
    }
}