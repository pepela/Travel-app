package com.peranidze.travel.launcher

import android.os.Bundle
import androidx.lifecycle.Observer
import com.peranidze.travel.base.BaseActivity
import com.peranidze.travel.main.MainActivity
import com.peranidze.travel.signin.SignInActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LauncherActivity : BaseActivity() {

    private val launcherViewModel: LauncherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launcherViewModel.getUserIsLoggedInLiveData()
            .observe(this, Observer {
                if (it) startMain()
                else startLogin()
            })
    }

    override fun onResume() {
        super.onResume()
        launcherViewModel.checkUserIsLoggedIn()
    }

    private fun startLogin() {
        startActivity(SignInActivity.getIntent(this))
        finish()
    }

    private fun startMain() {
        startActivity(MainActivity.getIntent(this))
        finish()
    }

}
