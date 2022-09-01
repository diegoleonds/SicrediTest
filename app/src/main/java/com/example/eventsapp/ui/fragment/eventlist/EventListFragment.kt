package com.example.eventsapp.ui.fragment.eventlist

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventsapp.R
import com.example.eventsapp.data.model.Result
import com.example.eventsapp.ui.extensions.getMessageResource
import com.example.eventsapp.ui.extensions.gone
import com.example.eventsapp.ui.extensions.visible
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventListFragment : Fragment(R.layout.fragment_events_list) {
    private val viewModel: EventListViewModel by viewModel()

    private lateinit var adapter: EventAdapter
    private lateinit var eventList: RecyclerView

    private lateinit var tryAgainBtn: Button
    private lateinit var errorMessage: TextView

    private lateinit var loadingCircle: CircularProgressIndicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateViews(view)
        initAdapter(view)
        setRecyclerViewAdapter()
        observeViewModelResult()
        fetchViewModelResult()
    }

    private fun inflateViews(view: View) {
        eventList = view.findViewById(R.id.events_rv)
        loadingCircle = view.findViewById(R.id.events_list_loading)
        tryAgainBtn = view.findViewById(R.id.try_again_btn)
        errorMessage = view.findViewById(R.id.erro_message_txt)
    }

    private fun initAdapter(view: View) {
        adapter = EventAdapter {  }
    }

    private fun setRecyclerViewAdapter() {
        eventList.adapter = adapter
        eventList.setHasFixedSize(true)
        eventList.layoutManager = LinearLayoutManager(context)
    }

    private fun setTryAgainBtnClick(){
        tryAgainBtn.setOnClickListener {
            fetchViewModelResult()
        }
    }

    private fun observeViewModelResult() {
        viewModel.result.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Success -> {
                    adapter.events = result.data
                    loadingCircle.gone()
                    tryAgainBtn.gone()
                    errorMessage.gone()

                    eventList.visible()
                }
                is Result.Fail -> {
                    loadingCircle.gone()
                    eventList.gone()

                    tryAgainBtn.visible()
                    setTryAgainBtnClick()

                    errorMessage.visible()
                    errorMessage.text = getString(result.error.getMessageResource())
                }
                is Result.Loading -> {
                    eventList.gone()
                    tryAgainBtn.gone()
                    errorMessage.gone()

                    loadingCircle.visible()
                }
            }
        }
    }

    private fun fetchViewModelResult() {
        viewModel.fetchEvents()
    }
}