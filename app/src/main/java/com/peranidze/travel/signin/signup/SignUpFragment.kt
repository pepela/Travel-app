package com.peranidze.travel.signin.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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

    private val signUpViewModel: SignupViewModel by viewModel()
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
            val email = sign_up_email_et.text.toString()
            val password = sign_up_password_et.text.toString()
            val repeatPassword = sign_up_repeat_password_et.text.toString()

            if (areFieldsCorrect(email, password, repeatPassword)) {
                signUpViewModel.doSignUp(email, password)
            } else {

            }
            showFieldErrors(sign_up_email_et, sign_up_password_et, sign_up_repeat_password_et)
        }
    }

    private fun areFieldsCorrect(email: String, password: String, repeatPassword: String) =
        email.isEmail() && isPasswordCorrect(password) && doPasswordsMatch(password, repeatPassword)

    private fun showFieldErrors(emailView: EditText, passwordView: EditText, repeatPasswordView: EditText) {
        val email = emailView.text.toString()
        val password = passwordView.text.toString()
        val repeatPassword = repeatPasswordView.text.toString()

        if (email.isEmpty()) {
            emailView.error = getString(R.string.err_empty_email)
        } else if (!email.isEmail()) {
            emailView.error = getString(R.string.err_incorrect_email)
        } else if (password.isEmpty()) {
            passwordView.error = getString(R.string.err_empty_password)
        } else if (!isPasswordCorrect(password)) {
            passwordView.error = getString(R.string.err_incorrect_password)
        } else if (repeatPassword.isEmpty()) {
            repeatPasswordView.error = getString(R.string.err_empty_repeat_password)
        } else if (!doPasswordsMatch(password, repeatPassword)) {
            repeatPasswordView.error = getString(R.string.err_passwords_do_not_match)
        }
    }

    private fun isPasswordCorrect(password: String) = password.length in 5..16

    private fun doPasswordsMatch(password: String, repeatPassword: String) = password == repeatPassword

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
