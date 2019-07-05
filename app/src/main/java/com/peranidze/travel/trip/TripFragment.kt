package com.peranidze.travel.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.peranidze.travel.R
import com.peranidze.travel.extensions.toDateString
import com.peranidze.travel.views.DatePickerFragment
import kotlinx.android.synthetic.main.fragment_trip.*
import java.util.*

class TripFragment : Fragment() {

    private var startDate: Date? = null
    private var endDate: Date? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_trip, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        val fm = fragmentManager

        trip_save_btn.setOnClickListener {
            findNavController().navigate(R.id.action_trip_dest_to_user_dest)
        }

        val startDateTv = trip_start_date_tv
        startDateTv.setOnClickListener {
            with(DatePickerFragment.getInstance(null, endDate)) {
                onDateSelectedListener = object : DatePickerFragment.OnDateSelectedListener {
                    override fun onDateSelected(date: Date) {
                        startDate = date
                        startDateTv.text = date.toDateString()
                    }
                }
                fm?.let {
                    show(it, "datePickerStart")
                }
            }
        }

        val endDateTv = trip_end_date_tv
        endDateTv.setOnClickListener {
            with(DatePickerFragment.getInstance(startDate, null)) {
                onDateSelectedListener = object : DatePickerFragment.OnDateSelectedListener {
                    override fun onDateSelected(date: Date) {
                        endDate = date
                        endDateTv.text = date.toDateString()
                    }
                }
                fm?.let {
                    show(it, "datePickerEnd")
                }
            }
        }
    }
}
