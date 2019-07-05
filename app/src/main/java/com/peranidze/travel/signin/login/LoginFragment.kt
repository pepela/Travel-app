package com.peranidze.travel.signin.login

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.peranidze.data.user.model.User
import com.peranidze.travel.R
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    interface OnLoginFragmentInteractionListener {
        fun onLoggedIn(user: User)

        fun onCreateAccountClicked()

        fun showLoginLoading(show: Boolean)
    }

    companion object {
        fun createInstance(): LoginFragment = LoginFragment()

        val TAG = LoginFragment::class.java.name
    }

    val loginViewModel: LoginViewModel by viewModel()
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
            if (!isEmailValid(login_email_tv.text.toString())) {
                login_email_tv.error = getString(R.string.err_incorrect_email)
            } else {
                login(login_email_tv.text.toString(), login_password_tv.text.toString())
            }
        }
    }

    private fun isEmailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun login(email: String, password: String) {
        loginViewModel.doLogin(email, password)
    }

    private fun handleLoading() {
        listener.showLoginLoading(true)
    }

    private fun handleError(errorMessage: String?) {
        listener.showLoginLoading(false)
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun handleSuccess(user: User?) {
        listener.showLoginLoading(false)
        user?.let {
            listener.onLoggedIn(it)
        }
        Toast.makeText(context, user.toString(), Toast.LENGTH_LONG).show()
    }

}
