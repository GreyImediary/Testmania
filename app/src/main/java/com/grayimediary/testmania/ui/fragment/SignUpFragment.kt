package com.grayimediary.testmania.ui.fragment

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.grayimediary.testmania.*
import com.grayimediary.testmania.ui.activity.MainActivity
import com.grayimediary.testmania.viewModel.LogInViewModel
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpFragment : Fragment() {

    private val viewModel by sharedViewModel<LogInViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fr_sign_up_button_in.setOnClickListener { findNavController().popBackStack() }
        fr_sign_up_button_sign.setOnClickListener { signUpClick() }

        observeLiveData()
    }

    private fun signUpClick() {
        try {
            assertEditInput(fr_sign_up_login, getString(R.string.screen_log_login_error))
            assertEditInput(fr_sign_up_email, getString(R.string.screen_log_email_error))
            assertEditInput(fr_sign_up_password, getString(R.string.screen_log_password_error))

            val login = fr_sign_up_login_edit.text.toString()
            val email = fr_sign_up_email_edit.text.toString()
            val password = fr_sign_up_password_edit.text.toString()

            val emailRegex = Regex("""[a-zA-Z]+@[a-zA-Z]+\.[a-zA-Z]+""")

            if (!emailRegex.matches(email)) {
                fr_sign_up_email.error = getString(R.string.fragment_sign_up_email_valid_error)
                return
            } else {
                fr_sign_up_email.error = null
            }

            viewModel.createUser(login, email, password)

        } catch (t: NullPointerException) {}
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

        viewModel.userLive.observe(this, Observer {
            if (it != null) {

                context?.getSharedPreferences(prefName, MODE_PRIVATE)?.edit {
                    putString(LOGIN_KEY, it.login)
                }

                val intent = Intent(activity, MainActivity::class.java).apply {
                    putExtra(USER_EXTRA, it)
                }

                context?.startActivity(intent)
                activity?.finish()
            }
        })
    }
}