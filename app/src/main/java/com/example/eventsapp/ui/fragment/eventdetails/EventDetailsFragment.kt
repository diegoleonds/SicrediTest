package com.example.eventsapp.ui.fragment.eventdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.eventsapp.R
import com.example.eventsapp.data.model.Event
import com.example.eventsapp.databinding.FragmentEventDetailsBinding
import com.example.eventsapp.ui.extensions.toDateString
import com.example.eventsapp.ui.extensions.view.getPersonFromSharedPreferences
import com.example.eventsapp.ui.extensions.view.gone
import com.example.eventsapp.ui.fragment.base.BaseFragment
import com.example.eventsapp.ui.glide.ImgLoader
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventDetailsFragment :
    BaseFragment<FragmentEventDetailsBinding>(R.layout.fragment_event_details) {
    private val viewModel: EventDetailsViewModel by viewModel()
    private lateinit var event: Event

    companion object {
        const val EVENT_BUNDLE = "event_bundle"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackFabClick(view)
        getDataFromBundle()
        setReadMoreTxtClick()
        setJoinBtnClick()
        observeViewModelResult()
    }

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) = FragmentEventDetailsBinding.inflate(inflater, container, attachToParent)

    private fun setBackFabClick(view: View) {
        binding.eventInfoBackFab.setOnClickListener {
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
                imgView = binding.eventImg
            )

            with(binding) {
                eventInfoDate.text = event.date.toDateString()
                eventName.text = event.title
                eventInfoPrice.text = event.price.toString()
                eventInfoDescription.text = event.description
                eventInfoPeople.text = event.people.size.toString()
            }
        }
    }

    private fun setReadMoreTxtClick() {
        binding.readMoreTxtClick.setOnClickListener {
            with(binding) {
                eventInfoDescription.maxLines = 1000
                readMoreTxtClick.gone()
            }
        }
    }

    private fun setJoinBtnClick() {
        binding.joinBtn.setOnClickListener {
            viewModel.eventCheckIn(event, activity?.getPersonFromSharedPreferences())
            binding.joinBtn.isEnabled = false
        }
    }

    private fun observeViewModelResult() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    binding.eventInfoPeople.text = (event.people.size + 1).toString()
                }
                is UiState.Error -> {
                    binding.joinBtn.isEnabled = true
                    Toast.makeText(
                        context, getString(state.messageResource),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}