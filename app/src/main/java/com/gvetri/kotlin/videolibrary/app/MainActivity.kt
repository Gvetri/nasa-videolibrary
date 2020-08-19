package com.gvetri.kotlin.videolibrary.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.nav_host_fragment)
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
    }
}
