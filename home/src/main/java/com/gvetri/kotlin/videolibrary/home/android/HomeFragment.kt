package com.gvetri.kotlin.videolibrary.home.android

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gvetri.kotlin.videolibrary.core.LoadingState
import com.gvetri.kotlin.videolibrary.core.repository.EventObserver
import com.gvetri.kotlin.videolibrary.core.viewBinding
import com.gvetri.kotlin.videolibrary.home.R
import com.gvetri.kotlin.videolibrary.home.databinding.FragmentHomeBinding
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import org.koin.android.ext.android.inject

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by inject()

    private val binding: FragmentHomeBinding? by viewBinding(FragmentHomeBinding::bind)

    private val nasaListAdapter = NasaListAdapter { nasaResultItem ->
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                nasaResultItem.href,
                nasaResultItem.dataList.firstOrNull()?.title
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupUI()
    }

    private fun setupUI() {
        binding?.apply {
            nasaItemList.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = nasaListAdapter
            }
        }
    }

    private fun setupObservers() {
        viewModel.nasaLiveData.observe(viewLifecycleOwner, ::onNewDataLoaded)

        viewModel.errorLiveData.observe(viewLifecycleOwner, EventObserver(::onError))

        viewModel.loadingLiveData.observe(viewLifecycleOwner, EventObserver(::onLoading))
    }

    private fun onNewDataLoaded(nasaSearchResult: NasaSearchResult) =
        nasaListAdapter.submitList(nasaSearchResult.items)

    private fun onLoading(onLoadingEvent: LoadingState) {
        when (onLoadingEvent) {
            LoadingState.Loading -> binding?.progressBar?.visibility = View.VISIBLE
            LoadingState.NotLoading -> binding?.progressBar?.visibility = View.GONE
        }
    }

    private fun onError(unit: Unit) {
        Toast.makeText(context, "Oops! Something has gone wrong", Toast.LENGTH_SHORT).show()
    }
}
