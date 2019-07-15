package com.peranidze.travel.base

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {

    fun showErrorMessage(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showToast(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showMessage(message: Int) {
        Snackbar.make(getCoordinateView(), message, Snackbar.LENGTH_LONG).show()
    }

    abstract fun getCoordinateView(): View
}
