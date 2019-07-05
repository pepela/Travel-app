package com.peranidze.travel.trips

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.peranidze.data.trip.model.Trip
import com.peranidze.travel.R
import kotlinx.android.synthetic.main.fragment_trips.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TripsFragment : Fragment() {

    val adapter: TripsAdapter by inject()
    val tripsViewModel: TripsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_trips, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeUsersLiveData()
        tripsViewModel.fetchTrips(1)
    }

    @SuppressLint("CheckResult")
    private fun setupRecyclerView() {
        trips_rv.adapter = adapter
        trips_rv.layoutManager = LinearLayoutManager(context)
        trips_rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter.clickSubject.subscribe {
            findNavController().navigate(R.id.action_trips_dest_to_trip)
        }
    }

    private fun observeUsersLiveData() {
        tripsViewModel.getTrips().observe(this, Observer {
            it?.let {
                when (it) {
                    is TripsState.Loading -> handleLoading()
                    is TripsState.Success -> handleSuccess(it.data)
                    is TripsState.Error -> handleError(it.errorMessage)
                }
            }
        })
    }

    private fun handleLoading() {
        trips_progress.visibility = VISIBLE
        trips_rv.visibility = GONE
        trips_empty_tv.visibility = GONE
    }

    private fun handleSuccess(trips: List<Trip>?) {
        trips_progress.visibility = GONE
        if (trips.isNullOrEmpty()) {
            trips_empty_tv.visibility = VISIBLE
        } else {
            trips_empty_tv.visibility = GONE
            trips_rv.visibility = VISIBLE
            adapter.trips = trips
        }
    }

    private fun handleError(message: String?) {
        trips_progress.visibility = GONE
        trips_empty_tv.visibility = VISIBLE
        trips_rv.visibility = GONE
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
