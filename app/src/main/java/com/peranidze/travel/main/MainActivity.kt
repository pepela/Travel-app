package com.peranidze.travel.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.peranidze.cache.PreferenceHelper
import com.peranidze.remote.HeaderInterceptor
import com.peranidze.travel.R
import com.peranidze.travel.base.BaseActivity
import com.peranidze.travel.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    companion object {
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    private val mainViewModel: MainViewModel by viewModel()
    private val preferenceHelper: PreferenceHelper by inject()
    private val headerInterceptor: HeaderInterceptor by inject()
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setHeaderBearer()
        setupDrawerNavigation()
        setupListener()
        observeLogoutLiveData()
        handleUsersMenuItem()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setHeaderBearer() {
        headerInterceptor.setSessionToken(preferenceHelper.token)
    }

    private fun setupDrawerNavigation() {
        with(findNavController(R.id.nav_host_fragment)) {
            nav_view.setupWithNavController(this)
            setupActionBarWithNavController(this, drawer_layout)

            appBarConfiguration = AppBarConfiguration(this.graph, drawer_layout)
            setupActionBarWithNavController(this, appBarConfiguration)
        }
    }

    private fun setupListener() {
        nav_view.menu.findItem(R.id.action_logout)?.let {
            it.setOnMenuItemClickListener {
                mainViewModel.logout()
                return@setOnMenuItemClickListener true
            }
        }
    }

    private fun observeLogoutLiveData() {
        mainViewModel.getLogoutLiveData().observe(this, Observer {
            setHeaderBearer()
            if (!it) startLogin()
            else showError()
        })
    }

    private fun handleUsersMenuItem() {
        nav_view.menu.findItem(R.id.users_dest).isVisible = preferenceHelper.isManager || preferenceHelper.isAdmin
    }

    private fun startLogin() {
        startActivity(SignInActivity.getIntent(this).apply {
            addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        or Intent.FLAG_ACTIVITY_NEW_TASK
            )
        })
    }

    private fun showError() {
        toast("Error")
    }

}
