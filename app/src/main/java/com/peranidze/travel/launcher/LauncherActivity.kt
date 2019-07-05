package com.peranidze.travel.launcher

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.peranidze.travel.base.BaseActivity
import com.peranidze.travel.signin.SignInActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LauncherActivity : BaseActivity() {

    val launcherViewModel: LauncherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launcherViewModel.getUserIsLoggedInLiveData()
            .observe(this, Observer {
                it?.let {
                    if (it) startLogin()
                    else startMain()
                }
            })
    }

    override fun onResume() {
        super.onResume()
        launcherViewModel.checkUserIsLoggedIn()
    }

    private fun startLogin() {
        startActivity(SignInActivity.getIntent(this))
    }

    private fun startMain() {
        Toast.makeText(this, "Not implemented yet", Toast.LENGTH_LONG).show()
    }

}
