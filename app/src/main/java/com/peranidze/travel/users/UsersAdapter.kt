package com.peranidze.travel.users

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.peranidze.data.user.model.Role
import com.peranidze.data.user.model.User
import com.peranidze.travel.R
import io.reactivex.subjects.PublishSubject

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    val clickSubject = PublishSubject.create<User>()
    var users: List<User> = emptyList()
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User) {
            with(itemView) {
                findViewById<TextView>(R.id.user_login_tv).text = user.login
                findViewById<TextView>(R.id.user_email_tv).text = user.email
                findViewById<TextView>(R.id.user_role_admin_tv).visibility =
                    if (user.roles.contains(Role.ADMIN)) VISIBLE else GONE
                findViewById<TextView>(R.id.user_role_manager_tv).visibility =
                    if (user.roles.contains(Role.MANAGER)) VISIBLE else GONE
                findViewById<TextView>(R.id.user_role_regular_tv).visibility =
                    if (user.roles.contains(Role.REGULAR)) VISIBLE else GONE
                setOnClickListener {
                    clickSubject.onNext(user)
                }
            }
        }
    }
}
