package com.peranidze.travel.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.peranidze.data.trip.model.Trip
import com.peranidze.data.user.model.User
import com.peranidze.travel.R
import com.peranidze.travel.extensions.toDateString
import com.peranidze.travel.views.DatePickerFragment
import kotlinx.android.synthetic.main.fragment_trip.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class TripFragment : Fragment() {

    private val tripViewModel: TripViewModel by viewModel()
    private var startDate: Date? = null
    private var endDate: Date? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_trip, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeTripLiveData()

        arguments?.let {
            with(TripFragmentArgs.fromBundle(it)) {
                if (tripId > 0) {
                    if (isForAdmin) {
                        tripViewModel.fetchTripAndUsers(tripId)
                    } else {
                        tripViewModel.fetchTrip(tripId)
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        trip_save_btn.setOnClickListener {
        }

        setupStartDateClickListener()
        setupEndDateClickListener()

        trip_add_user_btn.setOnClickListener {
            findNavController().navigate(R.id.action_trip_dest_to_user_dest)
        }
    }

    private fun setupStartDateClickListener() {
        val fm = fragmentManager
        val startDateTv = trip_start_date_btn
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
    }

    private fun setupEndDateClickListener() {
        val fm = fragmentManager
        val endDateTv = trip_end_date_btn
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

    private fun observeTripLiveData() {
        tripViewModel.getTripLiveData().observe(this, androidx.lifecycle.Observer {
            it.let {
                when (it) {
                    is TripState.Loading -> handleTripLoading()
                    is TripState.Success -> handleTripSuccess(it.data)
                    is TripState.Error -> handleTripError(it.errorMessage)
                }
            }
        })

        tripViewModel.getTripAndUsersLiveData().observe(this, androidx.lifecycle.Observer {
            it.let {
                when (it) {
                    is TripAndUsersState.Loading -> handleTripAndUsersLoading()
                    is TripAndUsersState.Success -> handleTripAndUsersSuccess(it.data)
                    is TripAndUsersState.Error -> handleTripAndUsersError(it.errorMessage)
                }
            }
        })
    }

    private fun handleTripLoading() {
        trip_progress.visibility = VISIBLE
        trip_group.visibility = GONE
    }

    private fun handleTripSuccess(trip: Trip?) {
        trip_progress.visibility = GONE
        trip_group.visibility = VISIBLE
        trip?.let {
            trip_destination_et.setText(it.destination)
            trip_start_date_btn.text = it.startDate.toDateString()
            trip_end_date_btn.text = it.endDate.toDateString()
            trip_comment_et.setText(it.comment)
        }
    }

    private fun handleTripError(message: String?) {
        trip_progress.visibility = GONE
        trip_group.visibility = VISIBLE
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun handleTripAndUsersLoading() {
        trip_progress.visibility = VISIBLE
        trip_group.visibility = GONE
        trip_users_group.visibility = GONE
    }

    private fun handleTripAndUsersSuccess(pair: Pair<Trip, List<User>>?) {
        trip_progress.visibility = GONE
        trip_group.visibility = VISIBLE
        trip_users_group.visibility = VISIBLE
        pair?.let {
            with(pair.first) {
                trip_destination_et.setText(destination)
                trip_start_date_btn.text = startDate.toDateString()
                trip_end_date_btn.text = endDate.toDateString()
                trip_comment_et.setText(comment)
            }
            with(pair.second) {
                context?.let {
                    val arrayAdapter = UserAdapter(it, this)
                    trip_users_spinner.adapter = arrayAdapter
                }
            }
        }
    }

    private fun handleTripAndUsersError(message: String?) {
        trip_progress.visibility = GONE
        trip_group.visibility = VISIBLE
        trip_users_group.visibility = VISIBLE
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
