package com.gvetri.kotlin.videolibrary.app

import android.annotation.TargetApi
import android.app.PictureInPictureParams
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.gvetri.kotlin.videolibrary.app.databinding.ActivityMainBinding
import com.gvetri.kotlin.videolibrary.core.MainViewModel
import com.gvetri.kotlin.videolibrary.core.extensions.hasPipSupport
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModel<MainViewModel>()
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val bottomNav = binding?.bottomNav
        val navController = navHostFragment.navController
        bottomNav?.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(::setDestinationChangedListener)
        initObservers()
    }

    private fun setDestinationChangedListener(
        navController: NavController,
        navDestination: NavDestination,
        bundle: Bundle?
    ) {
        binding?.apply {
            if (navDestination.id == R.id.splashFragment) {
                binding?.bottomNav?.visibility = View.GONE
                supportActionBar?.hide()
            } else {
                binding?.bottomNav?.visibility = View.VISIBLE
                supportActionBar?.show()
            }
        }
    }

    private fun initObservers() {
        mainViewModel.isPlayingLiveData.observe(this, ::observePlayingVideo)
    }

    private fun observePlayingVideo(isPlaying: Boolean) {
        if (isPlaying) {
            if (hasPipSupport()) {
                setPictureInPictureParams(updatePipParameters())
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun updatePipParameters(): PictureInPictureParams = PictureInPictureParams.Builder()
        .setActions(emptyList())
        .build()

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (hasPipSupport() && mainViewModel.isPlaying()) {
            enterPictureInPictureMode(updatePipParameters())
        }
    }
}
