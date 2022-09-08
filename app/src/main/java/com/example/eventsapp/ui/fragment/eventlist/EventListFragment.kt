package com.example.eventsapp.ui.fragment.eventlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.eventsapp.R
import com.example.eventsapp.databinding.FragmentEventsListBinding
import com.example.eventsapp.ui.extensions.view.gone
import com.example.eventsapp.ui.extensions.view.visible
import com.example.eventsapp.ui.fragment.base.BaseFragment
import com.example.eventsapp.ui.fragment.eventdetails.EventDetailsFragment
import com.example.eventsapp.ui.glide.ImgLoader
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventListFragment : BaseFragment<FragmentEventsListBinding>(R.layout.fragment_events_list) {

    private val viewModel: EventListViewModel by viewModel()
    private lateinit var eventsAdapter: EventAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter(view)
        setRecyclerViewAdapter()
        observeViewModelResult()
        fetchViewModelResult()
    }

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) = FragmentEventsListBinding.inflate(inflater, container, attachToParent)


    private fun initAdapter(view: View) {
        eventsAdapter = EventAdapter(ImgLoader(Glide.with(view.context))) { event ->
            val bundle = bundleOf(Pair(EventDetailsFragment.EVENT_BUNDLE, event))
            Navigation.findNavController(view)
                .navigate(R.id.action_eventListFragment_to_eventDetailsFragment, bundle)
        }
    }

    private fun setRecyclerViewAdapter() {
        with(binding.eventsRv) {
            adapter = eventsAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setTryAgainBtnClick() {
        binding.tryAgainBtn.setOnClickListener {
            fetchViewModelResult()
        }
    }

    private fun observeViewModelResult() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            with(binding) {
                when (state) {
                    is UiState.Success -> {
                        eventsAdapter.events = state.events
                        eventsListLoading.gone()
                        tryAgainBtn.gone()
                        errorMessageTxt.gone()

                        binding.eventsRv.visible()
                    }
                    is UiState.Error -> {
                        binding.eventsListLoading.gone()
                        binding.eventsRv.gone()

                        binding.tryAgainBtn.visible()
                        setTryAgainBtnClick()

                        binding.errorMessageTxt.visible()
                        binding.errorMessageTxt.text = getString(state.messageResource)
                    }
                    is UiState.Loading -> {
                        binding.eventsRv.gone()
                        binding.tryAgainBtn.gone()
                        binding.errorMessageTxt.gone()

                        binding.eventsListLoading.visible()
                    }
                }
            }
        }
    }

    private fun fetchViewModelResult() {
        viewModel.fetchEvents()
    }
}