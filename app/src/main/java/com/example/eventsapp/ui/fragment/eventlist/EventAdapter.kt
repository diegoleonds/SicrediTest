package com.example.eventsapp.ui.fragment.eventlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.eventsapp.R
import com.example.eventsapp.data.model.Event
import com.example.eventsapp.ui.extensions.toDateString
import com.example.eventsapp.ui.glide.ImgLoader

class EventAdapter(
    private val imgLoader: ImgLoader,
    private val click: (event: Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    var events: List<Event> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val event = events[position]
            imgLoader.loadImage(event.image, eventImg)
            eventName.text = event.title
            eventDate.text = event.date.toDateString()
            eventPrice.text = event.price.toString()
            card.setOnClickListener { click(event) }
        }
    }

    override fun getItemCount() = events.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventName: TextView = view.findViewById(R.id.event_item_name)
        val eventDate: TextView = view.findViewById(R.id.event_item_date)
        val eventPrice: TextView = view.findViewById(R.id.event_item_price)
        val eventImg: ImageView = view.findViewById(R.id.item_event_img)
        val card: ConstraintLayout = view.findViewById(R.id.event_item_layout)
    }
}