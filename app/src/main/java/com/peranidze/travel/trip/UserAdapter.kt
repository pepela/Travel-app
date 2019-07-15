package com.peranidze.travel.trip

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.peranidze.data.user.model.User
import com.peranidze.travel.R

class UserAdapter(private val context: Context, private val users: List<User>) : BaseAdapter(), SpinnerAdapter {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView?.let { it }
            ?: View.inflate(context, R.layout.spinner_drop_down_item_user, null)
        view.findViewById<TextView>(R.id.user_login_tv).text = users[position].login
        view.findViewById<TextView>(R.id.user_role_tv).text = users[position].roles.min()!!.title

        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView?.let { it } ?: View.inflate(context, R.layout.spinner_item_user, null)
        view.findViewById<TextView>(R.id.user_login_tv).text = users[position].login

        return view
    }

    override fun getItem(position: Int): User = users[position]

    override fun getItemId(position: Int): Long = 0

    override fun getCount(): Int = users.size

}
