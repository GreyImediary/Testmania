package com.grayimediary.testmania.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.grayimediary.testmania.R
import com.grayimediary.testmania.RESULT_EXTRA
import com.grayimediary.testmania.TEST_EXTRA
import com.grayimediary.testmania.model.Test
import com.grayimediary.testmania.ui.adapter.ResultAdapter
import kotlinx.android.synthetic.main.activity_test_results.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_results)
        val colorRes: Int

        val results = intent.getSerializableExtra(RESULT_EXTRA) as ArrayList<Pair<String, Boolean>>

        val resultBooleans = results.map { it.second }
        val passed = resultBooleans.count { it }
        val percent = passed.toDouble() / resultBooleans.count().toDouble()

        test_list.setOnClickListener {
            finish()
        }

        when {
            percent < 0.5 -> {
                result_container.background = getDrawable(R.drawable.background_finish_screen_bad)
                colorRes = R.color.colorResultRed
            }
            percent > 0.75 -> {
                result_container.background = getDrawable(R.drawable.background_finish_screen_good)
                colorRes = R.color.colorResultGreen
            }
            else -> {
                result_container.background = getDrawable(R.drawable.background_finish_screen_normal)
                colorRes = R.color.colorResultOrange
            }
        }


        rv_test_results.adapter = ResultAdapter(results, colorRes)
        rv_test_results.layoutManager = LinearLayoutManager(this)
    }
}
