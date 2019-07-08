package com.peranidze.travel.base

import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    fun showErrorMessage(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
