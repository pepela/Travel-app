package com.peranidze.travel.user

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.peranidze.data.user.model.Role
import com.peranidze.data.user.model.User
import com.peranidze.travel.R
import com.peranidze.travel.base.BaseFragment
import com.peranidze.travel.extensions.makeGone
import com.peranidze.travel.extensions.makeVisible
import kotlinx.android.synthetic.main.fragment_user.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : BaseFragment() {

    private val userViewModel: UserViewModel by viewModel()
    private lateinit var currentUser: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_user, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupListener()
        setupForCreateUpdate()
        observeUserLiveData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_delete)?.isVisible = getUserLogin() != null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            with(getUserLogin()) {
                if (this != null) {
                    userViewModel.deleteUser(this)
                    return true
                }
            }
            return false
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupForCreateUpdate() {
        with(getUserLogin()) {
            if (this != null) {
                userViewModel.fetchUser(this)
                user_login_et.isEnabled = false
            } else {
                handleRolesVisibility()
            }
        }
    }

    private fun setupListener() {
        user_save_btn.setOnClickListener {
            if (getUserLogin() == null) {
                userViewModel.createUser(getLogin(), getEmail(), getSelectedUserRoles())
            } else {
                if (this::currentUser.isInitialized) {
                    userViewModel.updateUser(
                        currentUser.copy(login = getLogin(), email = getEmail(), roles = getSelectedUserRoles())
                    )
                }
            }
        }
    }

    private fun observeUserLiveData() {
        userViewModel.getUserLiveData().observe(this, Observer {
            it?.let {
                when (it) {
                    is GetUserState.Loading -> showLoading()
                    is GetUserState.Success -> showUser(it.data)
                    is GetUserState.Error -> showError(it.errorMessage)
                }
            }
        })

        userViewModel.createUserLiveData().observe(this, Observer {
            it?.let {
                when (it) {
                    is CreateUserState.Loading -> showLoading()
                    is CreateUserState.Success -> {
                        showUser(it.data)
                        showToast(R.string.msg_user_create_success)
                    }
                    is CreateUserState.Error -> showError(it.errorMessage)
                }
            }
        })

        userViewModel.deleteUserLiveData().observe(this, Observer {
            it?.let {
                when (it) {
                    is DeleteUserState.Loading -> showLoading()
                    is DeleteUserState.Success -> {
                        showToast(R.string.msg_user_delete_success)
                        findNavController().navigateUp()
                    }
                    is DeleteUserState.Error -> showError(it.errorMessage)
                }
            }
        })

        userViewModel.updateUserLiveData().observe(this, Observer {
            it?.let {
                when (it) {
                    is UpdateUserState.Loading -> showLoading()
                    is UpdateUserState.Success -> {
                        showUser(it.data)
                        showToast(R.string.msg_user_update_success)
                    }
                    is UpdateUserState.Error -> showError(it.errorMessage)
                }
            }
        })
    }

    private fun showLoading() {
        user_progress.makeVisible()
    }

    private fun showUser(user: User?) {
        user_progress.makeGone()
        if (user == null) {
            findNavController().popBackStack()
        } else {
            currentUser = user
            user_login_et.setText(user.login)
            user_email_et.setText(user.email)

            handleRolesVisibility()

            user_role_regular_cb.isChecked = user.roles.contains(Role.REGULAR)
            user_role_manager_cb.isChecked = user.roles.contains(Role.MANAGER)
            user_role_admin_cb.isChecked = user.roles.contains(Role.ADMIN)
        }
    }

    private fun showError(message: String?) {
        user_progress.makeGone()
        showErrorMessage(message)
    }

    private fun handleRolesVisibility() {
        when {
            isForManager() -> {
                user_role_regular_cb.makeVisible()
                user_role_manager_cb.makeVisible()
                user_role_admin_cb.makeGone()
            }
            isForAdmin() -> {
                user_role_regular_cb.makeVisible()
                user_role_manager_cb.makeVisible()
                user_role_admin_cb.makeVisible()
            }
        }
    }

    private fun getUserLogin(): String? =
        arguments?.let {
            UserFragmentArgs.fromBundle(it).userLogin
        }

    private fun isForAdmin(): Boolean =
        arguments?.let { bundle ->
            UserFragmentArgs.fromBundle(bundle).isForAdmin
        } ?: false

    private fun isForManager(): Boolean =
        arguments?.let { bundle ->
            UserFragmentArgs.fromBundle(bundle).isForManager
        } ?: false

    private fun getLogin(): String = user_login_et.text.toString()

    private fun getEmail(): String = user_email_et.text.toString()

    private fun getSelectedUserRoles(): List<Role> {
        val roles = ArrayList<Role>()
        if (user_role_regular_cb.isChecked) roles.add(Role.REGULAR)
        if (user_role_manager_cb.isChecked) roles.add(Role.MANAGER)
        if (user_role_admin_cb.isChecked) roles.add(Role.ADMIN)
        return roles
    }
}
