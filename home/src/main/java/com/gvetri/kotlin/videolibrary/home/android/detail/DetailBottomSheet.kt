package com.gvetri.kotlin.videolibrary.home.android.detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gvetri.kotlin.videolibrary.core.MainViewModel
import com.gvetri.kotlin.videolibrary.core.repository.EventObserver
import com.gvetri.kotlin.videolibrary.home.R
import com.gvetri.kotlin.videolibrary.home.databinding.FragmentDetailBinding
import com.gvetri.kotlin.videolibrary.model.error.NasaError
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class DetailBottomSheet : BottomSheetDialogFragment() {

    private val viewModel: DetailViewModel by inject()
    private val sharedViewModel by sharedViewModel<MainViewModel>()
    private var binding: FragmentDetailBinding? = null
    private val arguments: DetailBottomSheetArgs by navArgs()
    private var simplePlayer: SimpleExoPlayer? = null

    private val playerListener = object : Player.EventListener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            sharedViewModel.updateIsVideoPlaying(isPlaying)
        }
    }


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
            simplePlayer?.addListener(playerListener)
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
        sharedViewModel.updateIsVideoPlaying(false)
        releasePlayer()
        binding = null
    }

    private fun setMediaItem(url: String) {
        val mediaItem: MediaItem =
            MediaItem.fromUri(url)
        simplePlayer?.apply {
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode)
        if (isInPictureInPictureMode) {
            binding?.title?.visibility = View.GONE
            setFullScreenPlayer()
        } else {
            setOriginalPlayerState()
            binding?.title?.visibility = View.VISIBLE
        }
    }

    private fun setOriginalPlayerState() {
        binding?.apply {
            playerView.apply {
                val density = resources.displayMetrics.density
                val dp = 240 * density
                layoutParams = ConstraintLayout.LayoutParams(0, dp.toInt())
                binding?.root?.removeView(this)
                binding?.root?.addView(this, 0)
                val constrainSet = ConstraintSet()
                constrainSet.clone(binding?.root)
                constrainSet.connect(
                    id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START,
                    0
                )
                constrainSet.connect(
                    id,
                    ConstraintSet.TOP,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.TOP,
                    0
                )
                constrainSet.connect(
                    id,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END,
                    0
                )
                constrainSet.applyTo(binding?.root)

                title.layoutParams = ConstraintLayout.LayoutParams(0, 48)
                binding?.root?.removeView(this)
                binding?.root?.addView(this, 1)
                val titleConstraintSet = ConstraintSet()
                titleConstraintSet.clone(binding?.root)
                titleConstraintSet.connect(
                    title.id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START,
                    0
                )
                titleConstraintSet.connect(
                    title.id,
                    ConstraintSet.TOP,
                    id,
                    ConstraintSet.BOTTOM,
                    0
                )
                titleConstraintSet.connect(
                    title.id,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END,
                    0
                )
                constrainSet.applyTo(binding?.root)

            }
        }
    }

    private fun setFullScreenPlayer() {
        binding?.playerView?.apply {
            layoutParams = ConstraintLayout.LayoutParams(0, 0)
            binding?.root?.removeView(this)
            binding?.root?.addView(this, 0)
            val constrainSet = ConstraintSet()
            constrainSet.clone(binding?.root)
            constrainSet.connect(
                id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                0
            )
            constrainSet.connect(
                id,
                ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.LEFT,
                0
            )
            constrainSet.connect(
                id,
                ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.RIGHT,
                0
            )
            constrainSet.connect(
                id,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM,
                0
            )
            constrainSet.applyTo(binding?.root)
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