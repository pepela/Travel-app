package com.peranidze.travel.trip

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.peranidze.data.trip.model.Trip
import com.peranidze.data.user.model.User
import com.peranidze.travel.R
import com.peranidze.travel.base.BaseFragment
import com.peranidze.travel.extensions.makeGone
import com.peranidze.travel.extensions.makeVisible
import com.peranidze.travel.extensions.toDateString
import com.peranidze.travel.extensions.validate
import com.peranidze.travel.users.UsersState
import com.peranidze.travel.views.DatePickerFragment
import kotlinx.android.synthetic.main.fragment_trip.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class TripFragment : BaseFragment() {

    private val tripViewModel: TripViewModel by viewModel()
    private var tripStartDate: Date? = null
    private var tripEndDate: Date? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_trip, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupListeners()
        observeTripLiveData()

        val tripId = getTripId()
        val isForAdmin = isForAdmin()

        if (isForAdmin && tripId > 0) {
            tripViewModel.fetchTripAndUsers(tripId)
        } else if (isForAdmin) {
            tripViewModel.fetchUsers()
        } else if (tripId > 0) {
            tripViewModel.fetchTrip(tripId)
        } else {
            trip_group.makeVisible()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_delete)?.isVisible = getTripId() > 0
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            tripViewModel.deleteTrip(getTripId())
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getTripId(): Long =
        arguments?.let {
            with(TripFragmentArgs.fromBundle(it)) {
                tripId
            }
        } ?: -1

    private fun isForAdmin() =
        arguments?.let {
            with(TripFragmentArgs.fromBundle(it)) {
                isForAdmin
            }
        } ?: false

    private fun isNewTrip(): Boolean = getTripId() <= 0

    private fun setupListeners() {
        setupSaveButtonClickListener()
        setupStartDateClickListener()
        setupEndDateClickListener()

        trip_add_user_btn.setOnClickListener {
            findNavController().navigate(R.id.action_trip_dest_to_user_dest)
        }
    }

    private fun setupSaveButtonClickListener() {
        trip_save_btn.setOnClickListener {
            if (validateFields()) {
                if (isNewTrip()) {
                    if (isForAdmin()) {
                        // TODO get user id from spinner
                    } else {
                        tripViewModel.createTrip(
                            null,
                            trip_destination_et.text.toString(),
                            tripStartDate!!,
                            tripEndDate!!,
                            trip_comment_et.text.toString()
                        )
                    }
                } else {
                    // TODO update
                    //tripViewModel.updateTrip(Trip())
                }
            }
        }
    }

    private fun setupStartDateClickListener() {
        val fm = fragmentManager
        val startDateTv = trip_start_date_btn
        startDateTv.setOnClickListener {
            with(DatePickerFragment.getInstance(null, tripEndDate)) {
                onDateSelectedListener = object : DatePickerFragment.OnDateSelectedListener {
                    override fun onDateSelected(date: Date) {
                        tripStartDate = date
                        startDateTv.error = null
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
            with(DatePickerFragment.getInstance(tripStartDate, null)) {
                onDateSelectedListener = object : DatePickerFragment.OnDateSelectedListener {
                    override fun onDateSelected(date: Date) {
                        tripEndDate = date
                        endDateTv.error = null
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
        tripViewModel.getTripLiveData().observe(this, Observer {
            it?.let {
                when (it) {
                    is TripState.Loading -> handleLoading()
                    is TripState.Success -> handleTripSuccess(it.data)
                    is TripState.Error -> handleTripError(it.errorMessage)
                }
            }
        })

        tripViewModel.getTripAndUsersLiveData().observe(this, Observer {
            it?.let {
                when (it) {
                    is TripAndUsersState.Loading -> handleLoading()
                    is TripAndUsersState.Success -> handleTripAndUsersSuccess(it.data)
                    is TripAndUsersState.Error -> handleTripAndUsersError(it.errorMessage)
                }
            }
        })

        tripViewModel.getUsersLiveData().observe(this, Observer {
            it?.let {
                when (it) {
                    is UsersState.Loading -> handleLoading()
                    is UsersState.Success -> handleUsersSuccess(it.data)
                    is UsersState.Error -> handleTripAndUsersError(it.errorMessage)
                }
            }
        })

        tripViewModel.getCreateTripLiveData().observe(this, Observer {
            it?.let {
                when (it) {
                    is TripState.Loading -> handleLoading()
                    is TripState.Success -> {
                        handleTripSuccess(it.data)
                        Toast.makeText(context, "Trip was created", Toast.LENGTH_LONG).show()
                    }
                    is TripState.Error -> handleTripError(it.errorMessage)
                }
            }
        })
    }

    private fun handleLoading() {
        trip_progress.makeVisible()
        trip_group.makeGone()
        trip_users_group.makeGone()
    }

    private fun handleTripSuccess(trip: Trip?) {
        trip_progress.makeGone()
        trip_group.makeVisible()
        if (trip != null) {
            showTrip(trip)
        } else {

        }
    }

    private fun handleTripError(message: String?) {
        trip_progress.makeGone()
        trip_group.makeVisible()
        showErrorMessage(message)
    }

    private fun handleTripAndUsersSuccess(pair: Pair<Trip, List<User>>?) {
        trip_progress.makeGone()
        trip_group.makeVisible()
        trip_users_group.makeVisible()
        pair?.let {
            showTrip(pair.first)
            with(pair.second) {
                trip_users_group.makeVisible()
                context?.let {
                    val arrayAdapter = UserAdapter(it, this)
                    trip_users_spinner.adapter = arrayAdapter
                    trip_users_spinner.setSelection(1) //TODO set correct user for trip
                }
            }
        }
    }

    private fun handleTripAndUsersError(message: String?) {
        trip_progress.makeGone()
        trip_group.makeVisible()
        trip_users_group.makeVisible()
        showErrorMessage(message)
    }

    private fun handleUsersSuccess(users: List<User>?) {
        trip_progress.makeGone()
        trip_group.makeVisible()
        trip_users_group.makeVisible()
        users?.let {
            context?.let {
                val arrayAdapter = UserAdapter(it, users)
                trip_users_spinner.adapter = arrayAdapter
            }
        }
    }

    private fun validateFields(): Boolean {
        if (trip_destination_et.validate(
                { trip_destination_et.text.toString().isNotEmpty() },
                R.string.err_empty_destination
            )
        ) {
            if (trip_start_date_btn.validate({ tripStartDate != null }, R.string.err_empty_start_date)) {
                if (trip_end_date_btn.validate({ tripEndDate != null }, R.string.err_empty_end_date)) {
                    return true
                }
            }
        }

        return false
    }

    private fun showTrip(trip: Trip) {
        with(trip) {
            tripStartDate = startDate
            tripEndDate = endDate
            trip_destination_et.setText(destination)
            trip_start_date_btn.text = startDate.toDateString()
            trip_end_date_btn.text = endDate.toDateString()
            trip_comment_et.setText(comment)
        }
    }

}
