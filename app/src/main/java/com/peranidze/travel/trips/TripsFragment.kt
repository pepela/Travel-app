package com.peranidze.travel.trips

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.peranidze.cache.PreferenceHelper
import com.peranidze.data.trip.model.Trip
import com.peranidze.data.user.model.Role
import com.peranidze.travel.R
import com.peranidze.travel.extensions.hideSoftKeyboard
import com.peranidze.travel.extensions.showSoftKeyboard
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_trips.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TripsFragment : Fragment() {

    private val adapter: TripsAdapter by inject()
    private val preferenceHelper: PreferenceHelper by inject()
    private val tripsViewModel: TripsViewModel by viewModel()

    private lateinit var adapterClickDisposable: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_trips, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupClickListeners()
        setupRecyclerView()
        setupAdapterClickListener()
        observeUsersLiveData()
        tripsViewModel.fetchTrips(1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (this::adapterClickDisposable.isInitialized) {
            adapterClickDisposable.dispose()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.trips_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setIconifiedByDefault(false)

        hideMagnifyingGlass(searchView)

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                searchView.requestFocusFromTouch()
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                searchView.setQuery("", true)
                return true
            }
        })

        searchView.setOnQueryTextFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.showSoftKeyboard()
            } else {
                view.hideSoftKeyboard()
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filterTrips(newText)
                return true
            }
        })
    }

    private fun hideMagnifyingGlass(searchView: SearchView) {
        try {
            val magId = resources.getIdentifier("android:id/search_mag_icon", null, null)
            val magImage = searchView.findViewById<ImageView>(magId)
            magImage.layoutParams = LinearLayout.LayoutParams(0, 0)
        } catch (t: Throwable) {
            Log.e(TripsFragment::class.java.name, t.message)
        }
    }

    private fun setupRecyclerView() {
        trips_rv.adapter = adapter
        trips_rv.layoutManager = LinearLayoutManager(context)
    }

    private fun setupAdapterClickListener() {
        adapterClickDisposable = adapter.clickSubject.subscribe {
            findNavController().navigate(
                TripsFragmentDirections.actionTripsDestToTrip(it.id, preferenceHelper.userRole == Role.ADMIN)
            )
        }
    }

    private fun setupClickListeners() {
        add_trip_fab.setOnClickListener {
            findNavController().navigate(
                TripsFragmentDirections.actionTripsDestToTrip(isForAdmin = preferenceHelper.userRole == Role.ADMIN)
            )
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
            adapter.allTrips = trips
        }
    }

    private fun handleError(message: String?) {
        trips_progress.visibility = GONE
        trips_empty_tv.visibility = VISIBLE
        trips_rv.visibility = GONE
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
