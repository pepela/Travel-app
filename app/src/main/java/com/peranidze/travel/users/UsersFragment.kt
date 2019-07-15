package com.peranidze.travel.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.peranidze.cache.PreferenceHelper
import com.peranidze.data.user.model.User
import com.peranidze.travel.R
import com.peranidze.travel.base.BaseFragment
import com.peranidze.travel.extensions.makeGone
import com.peranidze.travel.extensions.makeVisible
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_users.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : BaseFragment() {

    private val adapter: UsersAdapter by inject()
    private val preferenceHelper: PreferenceHelper by inject()
    private val usersViewModel: UsersViewModel by viewModel()

    private lateinit var adapterClickDisposable: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_users, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupAdapterClickListener()
        setupListeners()
        observeUsersLiveData()
        usersViewModel.fetchUsers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (this::adapterClickDisposable.isInitialized) {
            adapterClickDisposable.dispose()
        }
    }

    private fun setupRecyclerView() {
        users_rv.adapter = adapter
        users_rv.layoutManager = LinearLayoutManager(context)
        users_rv.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }

    private fun setupAdapterClickListener() {
        adapterClickDisposable = adapter.clickSubject.subscribe {
            findNavController().navigate(
                UsersFragmentDirections
                    .actionUsersDestToUser(it.login, preferenceHelper.isAdmin, preferenceHelper.isManager)
            )
        }
    }

    private fun setupListeners() {
        add_user_fab.setOnClickListener {
            findNavController().navigate(
                UsersFragmentDirections.actionUsersDestToUser(
                    null,
                    preferenceHelper.isAdmin,
                    preferenceHelper.isManager
                )
            )
        }

        users_swipe_refresh.setOnRefreshListener {
            usersViewModel.fetchUsers()
        }
    }

    private fun observeUsersLiveData() {
        usersViewModel.getUserLiveData().observe(this, Observer {
            it?.let {
                when (it) {
                    is UsersState.Loading -> handleLoading()
                    is UsersState.Success -> handleSuccess(it.data)
                    is UsersState.Error -> handleError(it.errorMessage)
                }
            }
        })
    }

    private fun handleLoading() {
        users_swipe_refresh.isRefreshing = true
        users_empty_tv.makeGone()
    }

    private fun handleSuccess(users: List<User>?) {
        users_swipe_refresh.isRefreshing = false
        if (users.isNullOrEmpty()) {
            users_empty_tv.makeVisible()
            adapter.users = emptyList()
        } else {
            users_empty_tv.makeGone()
            adapter.users = users
        }
    }

    private fun handleError(message: String?) {
        users_swipe_refresh.isRefreshing = false
        users_empty_tv.makeVisible()
        showErrorMessage(message)
    }
}
