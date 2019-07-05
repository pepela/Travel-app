package com.peranidze.travel.signin.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.peranidze.data.user.model.User
import com.peranidze.travel.R
import com.peranidze.travel.extensions.isEmail
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignUpFragment : Fragment() {

    interface OnSignUpFragmentInteractionListener {
        fun onSignedUp(user: User)

        fun showSignUpLoading(show: Boolean)
    }

    companion object {
        fun createInstance(): SignUpFragment = SignUpFragment()

        val TAG = SignUpFragment::class.java.name
    }

    val signupViewModel: SignupViewModel by viewModel()
    private lateinit var listener: OnSignUpFragmentInteractionListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_sign_up, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSignUpLiveData()
        setUpListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSignUpFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("Implement fragments OnSignUpFragmentInteractionListener")
        }
    }

    private fun observeSignUpLiveData() {
        signupViewModel.getSignUpLiveData().observe(this, Observer {
            when (it) {
                is SignUpState.Loading -> handleLoading()
                is SignUpState.Error -> handleError(it.errorMessage)
                is SignUpState.Success -> handleSuccess(it.data)
            }
        })
    }

    private fun setUpListener() {
        sign_up_btn.setOnClickListener {
            trySignUp(
                sign_up_email_tv.text.toString(),
                sign_up_password_tv.text.toString(),
                sign_up_repeat_password_tv.text.toString()
            )
        }
    }

    private fun trySignUp(email: String, password: String, repeatPassword: String) {
        if (!email.isEmail()) {
            sign_up_email_tv.error = getString(R.string.err_incorrect_email)
        } else if (!isPasswordCorrect(password, repeatPassword)) {
            sign_up_password_tv.error = getString(R.string.err_incorrect_password)
        } else {
            signupViewModel.doSignUp(email, password)
        }
    }

    private fun isPasswordCorrect(password: String, repeatPassword: String) =
        password.length in 5..15 && password == repeatPassword

    private fun handleLoading() {
        listener.showSignUpLoading(true)
    }

    private fun handleError(errorMessage: String?) {
        listener.showSignUpLoading(false)
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun handleSuccess(user: User?) {
        listener.showSignUpLoading(false)
        user?.let {
            listener.onSignedUp(it)
        }
    }
}
