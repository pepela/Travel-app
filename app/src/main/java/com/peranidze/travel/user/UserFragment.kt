package com.peranidze.travel.user

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.peranidze.data.user.model.Role
import com.peranidze.data.user.model.User
import com.peranidze.travel.R
import kotlinx.android.synthetic.main.fragment_user.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_user, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        observeUserLiveData()

        arguments?.let {
            with(UserFragmentArgs.fromBundle(it)) {
                if (userId > 0) {
                    userViewModel.fetchUser(userId)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            return true
        }
        return super.onOptionsItemSelected(item)
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
        user?.let {
            user_email_et.setText(user.email)

            arguments?.let { bundle ->
                with(UserFragmentArgs.fromBundle(bundle)) {
                    if (isForAdmin) {
                        // TODO set correct selection
                        user_roles_spinner.adapter = ArrayAdapter(
                            context,
                            android.R.layout.simple_spinner_dropdown_item,
                            listOf("REGULAR", "MANAGER", "ADMIN")
                        )
                    } else {
                        showRoleTextView(it.role)
                    }
                }
            } ?: showRoleTextView(it.role)
        }
    }

    private fun handleError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun showRoleTextView(role: Role) {
        user_roles_spinner.visibility = GONE
        user_role_tv.visibility = VISIBLE
        user_role_tv.text = role.toString()
    }
}
