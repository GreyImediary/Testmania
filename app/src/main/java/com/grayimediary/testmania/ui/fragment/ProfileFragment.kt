package com.grayimediary.testmania.ui.fragment

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.grayimediary.testmania.*
import com.grayimediary.testmania.model.User
import com.grayimediary.testmania.ui.activity.LogActivity
import com.grayimediary.testmania.ui.activity.MainActivity
import com.grayimediary.testmania.ui.activity.TestActivity
import com.grayimediary.testmania.ui.adapter.TestAdapter
import com.grayimediary.testmania.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main_profile.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ProfileFragment : Fragment() {

    private val viewModel by sharedViewModel<MainViewModel>()
    private lateinit var user: User

    companion object {
        fun newInstance(user: User): Fragment {
            val bundle = Bundle().apply {
                putSerializable(USER_EXTRA, user)
            }

            return ProfileFragment().apply {
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
        return inflater.inflate(R.layout.fragment_main_profile, null)
    }

    override fun onResume() {
        viewModel.getUser(user.id)
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = arguments?.getSerializable(USER_EXTRA) as User


        user_login.text = user.login
        exit_button.setOnClickListener {
            context?.getSharedPreferences(prefName, MODE_PRIVATE)?.edit {
                remove(LOGIN_KEY)
            }
            context?.startActivity(Intent(activity, LogActivity::class.java))
            activity?.finish()
        }

        viewModel.getCreatedTests(user.createdTests)
        viewModel.getFinishedTests(user.finishedTests)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.createdTestsLive.observe(this, Observer {
            val adapter = TestAdapter(
                user.login,
                onTestClick = { test -> },
                onDeleteClick = {
                    (activity as MainActivity).deleteTest(id)
                }
            ).apply {
                tests = it
            }

            rv_created_tests.adapter = adapter
            rv_created_tests.layoutManager = LinearLayoutManager(context)
        })

        viewModel.finishedTestsLive.observe(this, Observer {
            val adapter = TestAdapter(
                user.login,
                onTestClick = { test -> },
                onDeleteClick = { id -> }
            ).apply {
                tests = it
            }

            rv_passed_tests.adapter = adapter
            rv_passed_tests.layoutManager = LinearLayoutManager(context)
        })

        viewModel.userLive.observe(this, Observer {
            viewModel.getCreatedTests(it.createdTests)
            viewModel.getFinishedTests(it.finishedTests)
        })
    }
}