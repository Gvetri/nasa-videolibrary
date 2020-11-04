package com.gvetri.kotlin.videolibrary.home.android

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gvetri.kotlin.videolibrary.core.LoadingState
import com.gvetri.kotlin.videolibrary.core.repository.EventObserver
import com.gvetri.kotlin.videolibrary.core.viewBinding
import com.gvetri.kotlin.videolibrary.home.R
import com.gvetri.kotlin.videolibrary.home.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by inject()
    private val binding: FragmentHomeBinding? by viewBinding(FragmentHomeBinding::bind)
    private val nasaListAdapter = NasaListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.nasaLiveData.observe(
            viewLifecycleOwner,
            {
                nasaListAdapter.submitList(it.items)
                binding?.apply {
                    nasaItemList.apply {
                        layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        adapter = nasaListAdapter
                    }
                }
            }
        )

        viewModel.errorLiveData.observe(
            viewLifecycleOwner,
            EventObserver {
                Toast.makeText(context, "Oops! Something has gone wrong", Toast.LENGTH_SHORT).show()
            }
        )

        viewModel.loadingLiveData.observe(
            viewLifecycleOwner,
            EventObserver {
                when (it) {
                    LoadingState.Loading -> Toast.makeText(
                        context,
                        "Cargando...",
                        Toast.LENGTH_SHORT
                    ).show()

                    LoadingState.NotLoading -> Toast.makeText(
                        context,
                        "Información lista",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }
}
