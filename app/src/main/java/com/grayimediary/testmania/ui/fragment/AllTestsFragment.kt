package com.grayimediary.testmania.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.grayimediary.testmania.*
import com.grayimediary.testmania.model.User
import com.grayimediary.testmania.ui.activity.CreateTestActivity
import com.grayimediary.testmania.ui.activity.MainActivity
import com.grayimediary.testmania.ui.activity.TestActivity
import com.grayimediary.testmania.ui.adapter.TestAdapter
import com.grayimediary.testmania.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main_tests.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AllTestsFragment : Fragment() {

    private val viewModel by sharedViewModel<MainViewModel>()
    private lateinit var author: String
    private lateinit var user: User

    companion object {
        fun newInstance(author: String, user: User): Fragment {
            val bundle = Bundle().apply {
                putString(AUTHOR_EXTRA, author)
                putSerializable(USER_EXTRA, user)
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
        user = arguments?.getSerializable(USER_EXTRA) as User

        viewModel.getTests()

        observeLiveData()

        button_create_test.setOnClickListener {
            val intent = Intent(activity, CreateTestActivity::class.java).apply {
                putExtra(USER_ID, user.id)
            }
            activity?.startActivity(intent)
        }
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
                onTestClick = { test ->
                    val intent = Intent(activity, TestActivity::class.java).apply {
                        putExtra(TEST_EXTRA, test)
                        putExtra(USER_ID, user.id)
                    }

                    activity?.startActivity(intent)
                },
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