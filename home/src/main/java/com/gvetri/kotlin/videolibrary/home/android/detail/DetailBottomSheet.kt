package com.gvetri.kotlin.videolibrary.home.android.detail

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.gvetri.kotlin.videolibrary.core.repository.Event
import com.gvetri.kotlin.videolibrary.core.repository.EventObserver
import com.gvetri.kotlin.videolibrary.home.R
import com.gvetri.kotlin.videolibrary.home.databinding.FragmentDetailBinding
import com.gvetri.kotlin.videolibrary.model.error.NasaError
import org.koin.android.ext.android.inject


class DetailBottomSheet : BottomSheetDialogFragment() {

    private val viewModel: DetailViewModel by inject()
    private var binding: FragmentDetailBinding? = null
    private val arguments: DetailBottomSheetArgs by navArgs()
    private var simplePlayer: SimpleExoPlayer? = null

    override fun onStart() {
        super.onStart()
        dialog?.also(::setBottomSheetFullScreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            simplePlayer = SimpleExoPlayer.Builder(requireContext()).build()
            playerView.player = simplePlayer
            title.text = arguments.title
        }
        viewModel.retrieveVideoUrl(arguments.href)
        viewModel.videoUrlLiveData.observe(viewLifecycleOwner, ::setMediaItem)
        viewModel.errorLiveData.observe(viewLifecycleOwner, EventObserver(::showErrorSnackbar))
    }

    private fun showErrorSnackbar(nasaError: NasaError) {
        val errorText = when (nasaError.errorCode) {
            500 -> getString(R.string.server_error_ocurred)
            400 -> getString(R.string.error_no_video_found)
            else -> getString(R.string.general_error_message)
        }
        Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        releasePlayer()
        binding = null
    }

    private fun setMediaItem(url: String) {
        val mediaItem: MediaItem = MediaItem.fromUri(url)
        simplePlayer?.apply {
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }


    private fun releasePlayer() {
        simplePlayer?.apply { release() }
        simplePlayer = null
    }

    private fun setBottomSheetFullScreen(dialog: Dialog) {
        val bottomSheet = dialog.findViewById<View>(R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        val behavior = BottomSheetBehavior.from(bottomSheet!!)
        behavior.peekHeight = resources.displayMetrics.heightPixels
        behavior.isHideable = true
        view?.requestLayout()
    }
}