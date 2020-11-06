package com.gvetri.kotlin.videolibrary.app

import android.annotation.TargetApi
import android.app.PictureInPictureParams
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gvetri.kotlin.videolibrary.core.MainViewModel
import com.gvetri.kotlin.videolibrary.core.extensions.hasPipSupport
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.splashFragment) {
                findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE
                supportActionBar?.hide()
            } else {
                findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.VISIBLE
                supportActionBar?.show()
            }
        }
        initObservers()
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
