package com.peranidze.travel.signin.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import com.peranidze.data.user.model.User
import com.peranidze.travel.R
import com.peranidze.travel.base.BaseFragment
import com.peranidze.travel.extensions.isEmail
import com.peranidze.travel.extensions.validate
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignUpFragment : BaseFragment() {

    interface OnSignUpFragmentInteractionListener {
        fun onSignedUp(user: User)

        fun showSignUpLoading(show: Boolean)
    }

    companion object {
        fun createInstance(): SignUpFragment = SignUpFragment()

        val TAG = SignUpFragment::class.java.name
    }

    private val signUpViewModel: SignUpViewModel by viewModel()
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
        signUpViewModel.getSignUpLiveData().observe(this, Observer {
            when (it) {
                is SignUpState.Loading -> handleLoading()
                is SignUpState.Error -> handleError(it.errorMessage)
                is SignUpState.Success -> handleSuccess(it.data)
            }
        })
    }

    private fun setUpListener() {
        sign_up_btn.setOnClickListener {
            trySignUp(sign_up_email_et, sign_up_password_et, sign_up_repeat_password_et)
        }
    }

    private fun trySignUp(emailView: EditText, passwordView: EditText, repeatPasswordView: EditText) {
        val email = emailView.text.toString()
        val password = passwordView.text.toString()
        val repeatPassword = repeatPasswordView.text.toString()

        if (emailView.validate({ email.isNotEmpty() }, R.string.err_empty_email)) {
            if (emailView.validate({ email.isEmail() }, R.string.err_incorrect_email)) {
                if (passwordView.validate({ password.isNotEmpty() }, R.string.err_empty_password)) {
                    if (passwordView.validate({ isPasswordCorrect(password) }, R.string.err_incorrect_password)) {
                        if (repeatPasswordView.validate({ repeatPassword.isNotEmpty() }, R.string.err_empty_repeat_password)) {
                            if (repeatPasswordView.validate({ doPasswordsMatch(password, repeatPassword) }, R.string.err_passwords_do_not_match)) {
                                signUpViewModel.doSignUp(email, password)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun isPasswordCorrect(password: String) = password.length in 5..16

    private fun doPasswordsMatch(password: String, repeatPassword: String) = password == repeatPassword

    private fun handleLoading() {
        listener.showSignUpLoading(true)
    }

    private fun handleError(errorMessage: String?) {
        listener.showSignUpLoading(false)
        showErrorMessage(errorMessage)
    }

    private fun handleSuccess(user: User?) {
        listener.showSignUpLoading(false)
        user?.let {
            listener.onSignedUp(it)
        }
    }
}
