package com.peranidze.travel.trip

import android.os.Bundle
import android.view.*
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
    private var startDate: Date? = null
    private var endDate: Date? = null

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

    private fun isNewTrip(): Boolean = getTripId() > 0

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
            if (areFieldsCorrect()) {
                if (isNewTrip()) {
                    // TODO create
                } else {
                    // TODO update
                    //tripViewModel.updateTrip(Trip())
                }
            } else {
                showErrors()
            }
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
            with(DatePickerFragment.getInstance(startDate, null)) {
                onDateSelectedListener = object : DatePickerFragment.OnDateSelectedListener {
                    override fun onDateSelected(date: Date) {
                        endDate = date
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
    }

    private fun handleLoading() {
        trip_progress.makeVisible()
        trip_group.makeGone()
        trip_users_group.makeGone()
    }

    private fun handleTripSuccess(trip: Trip?) {
        trip_progress.makeGone()
        trip_group.makeVisible()
        trip?.let {
            trip_destination_et.setText(it.destination)
            trip_start_date_btn.text = it.startDate.toDateString()
            trip_end_date_btn.text = it.endDate.toDateString()
            trip_comment_et.setText(it.comment)
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
            with(pair.first) {
                trip_destination_et.setText(destination)
                trip_start_date_btn.text = startDate.toDateString()
                trip_end_date_btn.text = endDate.toDateString()
                trip_comment_et.setText(comment)
            }
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

    private fun areFieldsCorrect() =
        trip_destination_et.text.toString().isNotEmpty() && startDate != null && endDate != null

    private fun showErrors() {
        trip_destination_et.validate({ trip_destination_et.text.toString().isEmpty() }, R.string.err_empty_destination)
        if (startDate == null) trip_start_date_btn.error = getString(R.string.err_empty_start_date)
        if (endDate == null) trip_end_date_btn.error = getString(R.string.err_empty_end_date)
    }

}
