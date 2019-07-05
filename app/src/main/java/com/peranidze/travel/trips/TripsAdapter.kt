package com.peranidze.travel.trips

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.peranidze.data.trip.model.Trip
import com.peranidze.travel.R
import com.peranidze.travel.extensions.toDateString
import io.reactivex.subjects.PublishSubject

class TripsAdapter : RecyclerView.Adapter<TripsAdapter.ViewHolder>() {

    val clickSubject = PublishSubject.create<Trip>()
    var trips: List<Trip> = emptyList()
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_trip, parent, false))

    override fun getItemCount(): Int = trips.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(trips[position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(trip: Trip) {
            itemView.findViewById<TextView>(R.id.trip_from_tv).text = trip.destination
            itemView.findViewById<TextView>(R.id.trip_date_start_tv).text = trip.startDate.toDateString()
            itemView.findViewById<TextView>(R.id.trip_date_end_tv).text = trip.endDate.toDateString()
            itemView.setOnClickListener {
                clickSubject.onNext(trip)
            }
        }
    }

}
