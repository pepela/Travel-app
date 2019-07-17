package com.peranidze.travel.trips

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.peranidze.data.trip.model.Trip
import com.peranidze.travel.R
import com.peranidze.travel.extensions.makeGone
import com.peranidze.travel.extensions.makeVisible
import com.peranidze.travel.extensions.toDateString
import com.peranidze.travel.utils.DateUtils
import io.reactivex.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit

class TripsAdapter : RecyclerView.Adapter<TripsAdapter.ViewHolder>() {

    val clickSubject = PublishSubject.create<Trip>()
    var allTrips: List<Trip> = emptyList()
        set(value) {
            trips = value
            nextMonthTrips = value.filter { trip -> isTripInNextMonth(trip) }
            field = value
        }

    private var trips: List<Trip> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var nextMonthTrips: List<Trip> = emptyList()

    var filterQuery: String? = null
        set(value) {
            filterTrips(value)
            field = value
        }

    var isOnlyNextMonthSelected = false
        set(value) {
            field = value
            filterTrips(filterQuery)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_trip, parent, false))

    override fun getItemCount(): Int = trips.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(trips[position])
    }

    private fun filterTrips(destination: String?) {
        trips = if (destination.isNullOrEmpty()) {
            if (isOnlyNextMonthSelected) nextMonthTrips else allTrips
        } else {
            (if (isOnlyNextMonthSelected) nextMonthTrips else allTrips).filter {
                it.destination.toLowerCase().contains(destination.toLowerCase())
            }
        }
    }

    private fun isTripInNextMonth(trip: Trip) =
        with(DateUtils.getFirstDayOfNextMonth()) {
            trip.startDate.after(this)
                    || (trip.endDate.after(this) && trip.endDate.before(DateUtils.getLastDayOfNextMonth()))
        }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(trip: Trip) {
            with(itemView) {
                findViewById<TextView>(R.id.trip_from_tv).text = trip.destination
                findViewById<TextView>(R.id.trip_date_start_tv).text = trip.startDate.toDateString()
                findViewById<TextView>(R.id.trip_date_end_tv).text = trip.endDate.toDateString()

                val daysLeftTv: TextView = findViewById(R.id.trip_days_left_tv)
                if (isTripInFuture(trip)) {
                    daysLeftTv.makeVisible()
                    with(calculateDaysLeftTill(trip)) {
                        daysLeftTv.text = context.resources
                            .getQuantityString(
                                R.plurals.number_of_days_left, this, this
                            )
                    }
                } else {
                    daysLeftTv.makeGone()
                }

                setOnClickListener {
                    clickSubject.onNext(trip)
                }

                Glide.with(this)
                    .load(randomImageUrl())
                    .centerCrop()
                    .into(findViewById(R.id.trip_iv))
            }
        }

        private fun randomImageUrl() =
            listOf(
                "https://media.timeout.com/images/105240189/image.jpg",
                "https://s-ec.bstatic.com/images/hotel/max1024x768/149/149150580.jpg",
                "https://cdn.cnn.com/cnnnext/dam/assets/170831125949-cola-beach-goa.jpg",
                "https://www.turebi.ge/uploads/photos/tours1/large/40319_3.jpg?v=1",
                "https://cdn2.img.sputnik-georgia.com/images/22994/13/229941389.jpg",
                "https://www.telegraph.co.uk/content/dam/Travel/Destinations/Europe/United%20Kingdom/London/london-aerial-thames-guide-xlarge.jpg"
            ).shuffled()[0]

        private fun isTripInFuture(trip: Trip) = trip.startDate.after(Date())

        private fun calculateDaysLeftTill(trip: Trip): Int {
            if (!isTripInFuture(trip)) {
                return 0
            }
            return TimeUnit.DAYS.convert(trip.startDate.time - Date().time, TimeUnit.MILLISECONDS).toInt()
        }

    }

}
