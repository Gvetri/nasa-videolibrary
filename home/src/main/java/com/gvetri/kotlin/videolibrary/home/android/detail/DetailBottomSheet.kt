package com.gvetri.kotlin.videolibrary.home.android.detail

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gvetri.kotlin.videolibrary.home.R
import com.gvetri.kotlin.videolibrary.home.databinding.FragmentDetailBinding
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
            player.player = simplePlayer
            title.text = arguments.title
        }
        viewModel.retrieveVideoUrl(arguments.href)
        viewModel.videoUrlLiveData.observe(viewLifecycleOwner, ::setMediaItem)
    }

    private fun setMediaItem(url: String) {
        val uri = Uri.parse(url)
        val mediaItem: MediaItem = MediaItem.fromUri(uri)
        simplePlayer?.apply {
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    private fun setBottomSheetFullScreen(dialog: Dialog) {
        val bottomSheet = dialog.findViewById<View>(R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        val behavior = BottomSheetBehavior.from(bottomSheet!!)
        behavior.peekHeight = resources.displayMetrics.heightPixels
        behavior.isHideable = true
        view?.requestLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}