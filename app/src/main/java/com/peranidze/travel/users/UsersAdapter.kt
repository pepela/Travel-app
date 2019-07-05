package com.peranidze.travel.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.peranidze.data.user.model.User
import com.peranidze.travel.R
import io.reactivex.Observable
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
            itemView.findViewById<TextView>(R.id.user_email_tv).text = user.email
            itemView.findViewById<TextView>(R.id.user_role_tv).text = user.role.toString()
            itemView.setOnClickListener {
                clickSubject.onNext(user)
            }
        }
    }
}
