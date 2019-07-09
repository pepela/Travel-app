package com.peranidze.travel.signin.login

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
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {

    interface OnLoginFragmentInteractionListener {
        fun onLoggedIn(user: User)

        fun onCreateAccountClicked()

        fun showLoginLoading(show: Boolean)
    }

    companion object {
        fun createInstance(): LoginFragment = LoginFragment()

        val TAG = LoginFragment::class.java.name
    }

    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var listener: OnLoginFragmentInteractionListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLogin()
        setupListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLoginFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("Implement fragments OnLoginFragmentInteractionListener")
        }
    }

    private fun observeLogin() {
        loginViewModel.getLoginLiveData().observe(this, Observer {
            when (it) {
                is LoginState.Loading -> handleLoading()
                is LoginState.Error -> handleError(it.errorMessage)
                is LoginState.Success -> handleSuccess(it.data)
            }
        })
    }

    private fun setupListener() {
        login_sign_up_btn.setOnClickListener {
            listener.onCreateAccountClicked()
        }

        login_btn.setOnClickListener {
            tryLogin(login_email_et, login_password_et)
        }
    }

    private fun tryLogin(emailView: EditText, passwordView: EditText) {
        val email = emailView.text.toString()
        val password = passwordView.text.toString()

        if (emailView.validate({ email.isNotEmpty() }, R.string.err_empty_email)) {
            if (emailView.validate({ email.isEmail() }, R.string.err_incorrect_email)) {
                if (passwordView.validate({ password.isNotEmpty() }, R.string.err_empty_password)) {
                    login(email, password)
                }
            }
        }
    }

    private fun login(email: String, password: String) {
        loginViewModel.doLogin(email, password)
    }

    private fun handleLoading() {
        listener.showLoginLoading(true)
    }

    private fun handleError(errorMessage: String?) {
        listener.showLoginLoading(false)
        showErrorMessage(errorMessage)
    }

    private fun handleSuccess(user: User?) {
        listener.showLoginLoading(false)
        user?.let {
            listener.onLoggedIn(it)
        }
    }

}
