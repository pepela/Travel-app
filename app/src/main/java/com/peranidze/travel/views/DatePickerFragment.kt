package com.peranidze.travel.views

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    interface OnDateSelectedListener {
        fun onDateSelected(date: Date)
    }

    companion object {
        const val ARGS_MIN_DATE = "args_min_date"
        const val ARGS_MAX_DATE = "args_max_date"

        fun getInstance(start: Date?, end: Date?): DatePickerFragment {
            with(DatePickerFragment()) {
                with(Bundle()) {
                    putLong(ARGS_MIN_DATE, start?.time ?: Date().time)
                    putLong(ARGS_MAX_DATE, end?.time ?: Long.MAX_VALUE)
                    arguments = this
                }
                return this
            }
        }
    }

    lateinit var onDateSelectedListener: OnDateSelectedListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        context?.let {
            with(DatePickerDialog(it, this, year, month, day))
            {
                arguments?.let { arguments ->
                    datePicker.minDate = arguments.getLong(ARGS_MIN_DATE)
                    datePicker.maxDate = arguments.getLong(ARGS_MAX_DATE)
                }
                return this
            }
        }

        return super.onCreateDialog(savedInstanceState)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        onDateSelectedListener.onDateSelected(calendar.time)
    }

}
