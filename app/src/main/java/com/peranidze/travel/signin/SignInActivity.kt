package com.peranidze.travel.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.peranidze.cache.PreferenceHelper
import com.peranidze.data.user.model.Role
import com.peranidze.data.user.model.User
import com.peranidze.travel.R
import com.peranidze.travel.base.BaseActivity
import com.peranidze.travel.extensions.makeGone
import com.peranidze.travel.extensions.makeVisible
import com.peranidze.travel.main.MainActivity
import com.peranidze.travel.signin.login.LoginFragment
import com.peranidze.travel.signin.signup.SignUpFragment
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.koin.android.ext.android.inject

class SignInActivity : BaseActivity(), LoginFragment.OnLoginFragmentInteractionListener,
    SignUpFragment.OnSignUpFragmentInteractionListener {

    private val preferenceHelper: PreferenceHelper by inject()

    companion object {
        fun getIntent(context: Context) = Intent(context, SignInActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        showLoginFragment()
    }

    private fun showLoginFragment() {
        with(supportFragmentManager) {
            beginTransaction()
                .add(R.id.sign_in_container, LoginFragment.createInstance(), LoginFragment.TAG)
                .commit()
        }
    }

    private fun showSignUpFragment() {
        with(supportFragmentManager) {
            beginTransaction()
                .replace(R.id.sign_in_container, SignUpFragment.createInstance(), SignUpFragment.TAG)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onLoggedIn(user: User) {
        saveUser(user)
        startMain()
    }

    override fun onCreateAccountClicked() {
        showSignUpFragment()
    }

    override fun showLoginLoading(show: Boolean) {
        when (show) {
            true -> showLoading()
            false -> hideLoading()
        }
    }

    override fun onSignedUp(user: User) {
        saveUser(user)
        startMain()
    }

    override fun showSignUpLoading(show: Boolean) {
        when (show) {
            true -> showLoading()
            false -> hideLoading()
        }
    }

    private fun showLoading() {
        sign_in_progress.makeVisible()
    }

    private fun hideLoading() {
        sign_in_progress.makeGone()
    }

    private fun saveUser(user: User) {
        preferenceHelper.isUserLoggedIn = true
        preferenceHelper.userId = user.id
        preferenceHelper.token = user.token
        preferenceHelper.login = user.login
        preferenceHelper.isAdmin = user.roles.contains(Role.ADMIN)
        preferenceHelper.isManager = user.roles.contains(Role.MANAGER)
    }

    private fun startMain() {
        startActivity(MainActivity.getIntent(this))
        finish()
    }

}
