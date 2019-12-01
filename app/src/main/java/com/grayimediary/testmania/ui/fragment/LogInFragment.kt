package com.grayimediary.testmania.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.grayimediary.testmania.R
import kotlinx.android.synthetic.main.fragment_log_in.*

class LogInFragment : Fragment() {
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

    }
}