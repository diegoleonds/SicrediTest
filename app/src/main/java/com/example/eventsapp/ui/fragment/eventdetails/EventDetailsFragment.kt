package com.example.eventsapp.ui.fragment.eventdetails

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.eventsapp.R
import com.example.eventsapp.data.model.Event
import com.example.eventsapp.data.model.Result
import com.example.eventsapp.ui.extensions.getMessageResource
import com.example.eventsapp.ui.extensions.toDateString
import com.example.eventsapp.ui.extensions.view.getPersonFromSharedPreferences
import com.example.eventsapp.ui.extensions.view.gone
import com.example.eventsapp.ui.glide.ImgLoader
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventDetailsFragment : Fragment(R.layout.fragment_event_details) {
    private val viewModel: EventDetailsViewModel by viewModel()

    private lateinit var backFab: FloatingActionButton
    private lateinit var imgEvent: ImageView
    private lateinit var eventNameTxtView: TextView
    private lateinit var eventDateTxtView: TextView
    private lateinit var eventPriceTxtView: TextView
    private lateinit var eventDescriptionTxtView: TextView
    private lateinit var eventPeopleTxtView: TextView
    private lateinit var readMoreTxtClick: TextView
    private lateinit var joinBtn: Button

    private lateinit var event: Event

    companion object {
        const val EVENT_BUNDLE = "event_bundle"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateViews(view)
        setBackFabClick(view)
        getDataFromBundle()
        setReadMoreTxtClick()
        setJoinBtnClick()
        observeViewModelResult()
    }

    private fun inflateViews(view: View) {
        backFab = view.findViewById(R.id.event_info_back_fab)
        eventNameTxtView = view.findViewById(R.id.event_name)
        eventDateTxtView = view.findViewById(R.id.event_info_date)
        eventPriceTxtView = view.findViewById(R.id.event_info_price)
        eventDescriptionTxtView = view.findViewById(R.id.event_info_description)
        eventPeopleTxtView = view.findViewById(R.id.event_info_people)
        imgEvent = view.findViewById(R.id.event_img)
        readMoreTxtClick = view.findViewById(R.id.read_more_txt_click)
        joinBtn = view.findViewById(R.id.join_btn)
    }

    private fun setBackFabClick(view: View) {
        backFab.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }
    }

    private fun getDataFromBundle() {
        val bundleEvent = arguments?.getParcelable<Event>(EVENT_BUNDLE)
        bundleEvent?.let {
            event = it
            val imgLoader = ImgLoader(Glide.with(this))
            imgLoader.loadImage(
                imgUrl = event.image,
                imgView = imgEvent
            )

            eventDateTxtView.text = event.date.toDateString()
            eventNameTxtView.text = event.title
            eventPriceTxtView.text = event.price.toString()
            eventDescriptionTxtView.text = event.description
            eventPeopleTxtView.text = event.people.size.toString()
        }
    }

    private fun setReadMoreTxtClick() {
        readMoreTxtClick.setOnClickListener {
            eventDescriptionTxtView.maxLines = 1000
            readMoreTxtClick.gone()
        }
    }

    private fun setJoinBtnClick() {
        joinBtn.setOnClickListener {
            viewModel.eventCheckIn(event, activity?.getPersonFromSharedPreferences())
            joinBtn.isEnabled = false
        }
    }

    private fun observeViewModelResult() {
        viewModel.result.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Success -> {
                    eventPeopleTxtView.text = (event.people.size + 1).toString()
                }
                is Result.Fail -> {
                    joinBtn.isEnabled = true
                    Toast.makeText(context, getString(result.error.getMessageResource()),
                        Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {

                }
            }
        }
    }
}