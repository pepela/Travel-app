package com.peranidze.travel.user

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_user, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupListener()
        observeUserLiveData()

        with(getUserId()) {
            if (this > 0) {
                userViewModel.fetchUser(this)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_delete)?.isVisible = getUserId() > 0
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            with(getUserId()) {
                if (this > 0) {
                    userViewModel.deleteUser(this)
                    return true
                }
            }
            return false
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getUserId(): Long =
        arguments?.let {
            with(UserFragmentArgs.fromBundle(it)) {
                userId
            }
        } ?: -1

    private fun isForAdmin(): Boolean =
        arguments?.let { bundle ->
            with(UserFragmentArgs.fromBundle(bundle)) {
                isForAdmin
            }
        } ?: false

    private fun setupListener() {
        user_save_btn.setOnClickListener {
            if (getUserId() < 0) {
                //create
            } else {
                //userViewModel.updateUser(User(getUserId(), user_email_et.text.toString(),Role.REGULAR))
            }
        }
    }

    private fun observeUserLiveData() {
        userViewModel.getUserLiveData().observe(this, Observer {
            it?.let {
                when (it) {
                    is UserState.Loading -> handleLoading()
                    is UserState.Success -> handleSuccess(it.data)
                    is UserState.Error -> handleError(it.errorMessage)
                }
            }
        })
    }

    private fun handleLoading() {

    }

    private fun handleSuccess(user: User?) {
        if (user == null) {
            findNavController().popBackStack()
        } else {
            user_email_et.setText(user.email)

            if (isForAdmin()) {
                // TODO set correct selection
                user_roles_spinner.adapter = ArrayAdapter(
                    context,
                    android.R.layout.simple_spinner_dropdown_item,
                    listOf("REGULAR", "MANAGER", "ADMIN")
                )
            } else {
                showRoleTextView(user.role)
            }
        }
    }

    private fun handleError(message: String?) {
        showErrorMessage(message)
    }

    private fun showRoleTextView(role: Role) {
        user_roles_spinner.makeGone()
        user_role_tv.makeVisible()
        user_role_tv.text = role.toString()
    }
}
