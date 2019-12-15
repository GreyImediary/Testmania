package com.grayimediary.testmania.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.grayimediary.testmania.*
import com.grayimediary.testmania.ui.activity.MainActivity
import com.grayimediary.testmania.ui.adapter.TestAdapter
import com.grayimediary.testmania.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main_tests.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AllTestsFragment : Fragment() {

    private val viewModel by sharedViewModel<MainViewModel>()
    private lateinit var author: String

    companion object {
        fun newInstance(author: String): Fragment {
            val bundle = Bundle().apply {
                putString(AUTHOR_EXTRA, author)
            }

            return AllTestsFragment().apply {
                arguments = bundle
            }
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_tests, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        author = arguments?.getString(AUTHOR_EXTRA) ?: ""

        viewModel.getTests()

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.toastLive.observe(this, Observer {
            if (it != null) {
                context?.toast(it)
            }
        })

        viewModel.progressBarLive.observe(this, Observer {
            if (it != null) {
                if (it) {
                    progressBar.visible()
                } else {
                    progressBar.gone()
                }
            }
        })

        viewModel.testsLive.observe(this, Observer {
            val adapter = TestAdapter(
                author,
                onTestClick = { test -> },
                onDeleteClick = { id ->
                    (activity as MainActivity).deleteTest(id)
                }
            ).apply {
                tests = it
            }

            rv_all_tests.layoutManager = LinearLayoutManager(context)
            rv_all_tests.adapter = adapter
        })
    }
}