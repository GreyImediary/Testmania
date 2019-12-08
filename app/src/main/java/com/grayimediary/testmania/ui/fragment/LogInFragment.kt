package com.grayimediary.testmania.ui.fragment

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.grayimediary.testmania.*
import com.grayimediary.testmania.ui.activity.MainActivity
import com.grayimediary.testmania.viewModel.LogInViewModel
import kotlinx.android.synthetic.main.fragment_log_in.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LogInFragment : Fragment() {
    private val viewModel by sharedViewModel<LogInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val action = LogInFragmentDirections.actionLogInFragmentToSignUpFragment()

        fr_log_in_button_sign.setOnClickListener(Navigation.createNavigateOnClickListener(action))

        fr_log_in_button_in.setOnClickListener { logInClick() }

        observeLiveData()
    }

    private fun logInClick() {
        try {
            assertEditInput(fr_log_in_login, getString(R.string.screen_log_login_error))
            assertEditInput(fr_log_in_password, getString(R.string.screen_log_password_error))

            val login = fr_log_in_login_edit.text.toString()
            val password = fr_log_in_password_edit.text.toString()

            viewModel.authUser(login, password)
        } catch (t: NullPointerException) {
        }
    }

    private fun observeLiveData() {
        viewModel.progressBarLive.observe(this, Observer {
            if (it != null) {
                if (it) {
                    progressBar.visible()
                } else {
                    progressBar.gone()
                }
            }
        })

        viewModel.toastLive.observe(this, Observer {
            if (it != null) {
                context?.toast(it)
            }
        })

        viewModel.userLive.observe(this, Observer {
            context?.getSharedPreferences(prefName, MODE_PRIVATE)?.edit {
                putString(LOGIN_KEY, it.login)
            }

            val intent = Intent(activity, MainActivity::class.java).apply {
                putExtra(USER_EXTRA, it)
            }

            context?.startActivity(intent)
            activity?.finish()
        })
    }
}